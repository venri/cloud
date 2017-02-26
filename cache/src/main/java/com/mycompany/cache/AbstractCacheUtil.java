package com.mycompany.cache;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by root on 26.02.17.
 */

@Service
public interface AbstractCacheUtil<K, V> {

    public abstract void setDefault_timeout(long default_timeout) throws Exception;

    public abstract void put(K key, V data);

    public abstract void put(K key, V data, long timeout);

    public abstract V get(K key);

    public abstract void remove(K key);

    public abstract void removeAll();

    public abstract void setAll(Map<K, V> map);

    public abstract void addAll(Map<K, V> map);
}
