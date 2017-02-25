package com.mycompany.service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by Vadim on 12.02.2017.
 */
public class Main {
    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory;
        SubscriberMapper subscriberMapper;
        Reader reader = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            subscriberMapper = sqlSessionFactory.openSession().getMapper(SubscriberMapper.class);
            Subscriber subscriber = subscriberMapper.getSubscriberById(1);
            System.out.println(subscriber.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
