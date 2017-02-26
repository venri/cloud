package com.mycompany.cache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by Vadim on 08.01.2017.
 */
@SpringBootApplication
@EnableEurekaClient
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

    public void run(String... strings) throws Exception {

    }

}
