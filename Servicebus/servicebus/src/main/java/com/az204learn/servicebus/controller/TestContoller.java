package com.az204learn.servicebus.controller;

import jakarta.jms.ObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContoller {


    private static final String TOPIC_NAME = "mytopic";


    @Autowired
    private JmsTemplate jmsTemplate;

    @GetMapping("/check")
    public String getMessage() {
        return "Hi Service Bus";
    }


    @GetMapping("/sendmsg")
    public String sendMessage(String message) {
        jmsTemplate.send(TOPIC_NAME, session -> {
            ObjectMessage objectMessage = session.createObjectMessage(message);
            objectMessage.setStringProperty("ORDER","PROCESSING");
            objectMessage.setJMSMessageID("1234");
            return objectMessage;
        });
        return message;
    }





}
