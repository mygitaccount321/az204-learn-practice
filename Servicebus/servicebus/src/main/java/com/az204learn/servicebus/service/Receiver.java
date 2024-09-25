package com.az204learn.servicebus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Receiver {

    private static final String TOPIC_NAME = "mytopic";
    private static final String SUBSCRIPTION1_NAME = "S1";
    private static final String SUBSCRIPTION2_NAME = "S2";
    private static final String SUBSCRIPTION3_NAME = "S3";


    @JmsListener(destination = TOPIC_NAME, containerFactory = "topicJmsListenerContainerFactory",
            subscription = SUBSCRIPTION1_NAME)
    public void receiveMessageS1(String message) {
        log.info("S1 Message received: {}", message);
    }

    @JmsListener(destination = TOPIC_NAME, containerFactory = "topicJmsListenerContainerFactory",
            subscription = SUBSCRIPTION2_NAME)
    public void receiveMessageS2(String message) {
        log.info("S2 Message received: {}", message);
    }

    @JmsListener(destination = TOPIC_NAME, containerFactory = "topicJmsListenerContainerFactory",
            subscription = SUBSCRIPTION3_NAME)
    public void receiveMessageS3(String message) {
        log.info("S3 Message received: {}", message);
    }
}
