package com.mycompany.service;

import java.util.List;

/**
 * Created by Vadim on 12.02.2017.
 */
public interface SubscriberMapper {

    Subscriber getSubscriberById(Integer id);

    List getSubscriber();

}