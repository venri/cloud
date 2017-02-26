package com.mycompany.cache;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.*;

@Service
public class CacheUtil<K, V> implements AbstractCacheUtil<K, V>{

    private ConcurrentHashMap<Key, V> globalMap = new ConcurrentHashMap<>();
    private long default_timeout;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(
            new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    Thread th = new Thread(r);
                    th.setDaemon(true);
                    return th;
                }
            }
    );

    public CacheUtil() {
        this.default_timeout = 5000L;
        scheduler.scheduleAtFixedRate(() -> {
            long current = System.currentTimeMillis();
            for (Key k : globalMap.keySet()) {
                if (!k.isLive(current)) {
                    globalMap.remove(k);
                }
            }
        }, 1, default_timeout/5, TimeUnit.MILLISECONDS);
    }

    public CacheUtil(long default_timeout) {
        this.default_timeout = default_timeout;
        scheduler.scheduleAtFixedRate(() -> {
            long current = System.currentTimeMillis();
            for (Key k : globalMap.keySet()) {
                if (!k.isLive(current)) {
                    globalMap.remove(k);
                }
            }
        }, 1, default_timeout/5, TimeUnit.MILLISECONDS);
    }
    
    /**
     * @param default_timeout количество милисекунд - время которое обьект будет кранится в кеше
     */
    public void setDefault_timeout(long default_timeout) throws Exception {
        if (default_timeout < 100) {
            throw new Exception("Too short interval for storage in the cache. Interval should be more than 10 ms");
        }
        this.default_timeout = default_timeout;
    }

    /**
     * Метод для вставки обьекта в кеш
     * Время зранения берётся по умолчанию
     * @param key ключ в кеше
     * @param data данные
     */
    public void put(K key, V data) {
        globalMap.put(new Key(key, default_timeout), data);
    }

    /**
     * Метод для вставки обьекта в кеш
     * @param key ключ в кеше
     * @param data данные
     * @param timeout время хранения обьекта в кеше в милисекундах
     */
    public void put(K key, V data, long timeout) {
        globalMap.put(new Key(key, timeout), data);
    }

    /**
     * получение значения по ключу
     
     
     * @param key ключ для поиска с кеша
     * @return Обьект данных храняшийся в кеше
     */
    public V get(K key) {
        return globalMap.get(new Key(key));
    }

    /**
     * удаляет все значения по ключу из кеша

     * @param key - ключ
     */
    public void remove(K key) {
        globalMap.remove(new Key(key));
    }

    /**
     * Удаляет все значения из кеша
     */
    public void removeAll() {
        globalMap.clear();
    }

    /**
     * Полностью заменяет весь существующий кеш.
     * Время хранения по умолчанию.
     
     
     * @param map Карта с данными
     */
    public void setAll(Map<K, V> map) {
        ConcurrentHashMap tempmap = new ConcurrentHashMap<Key, V>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            tempmap.put(new Key(entry.getKey(), default_timeout), entry.getValue());
        }
        globalMap = tempmap;
    }

    /**
     * Добавляет к сущесвуещему кешу переданую карту
     * Время хранения по умолчанию.
     * @param map Карта с данными
     */
    public void addAll(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

}
