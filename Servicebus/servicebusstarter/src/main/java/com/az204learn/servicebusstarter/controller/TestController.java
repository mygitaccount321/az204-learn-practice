package com.az204learn.servicebusstarter.controller;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private ServiceBusSenderClient serviceBusSenderClient;

    @Autowired
    private ServiceBusSenderClient serviceBusSenderClient1;


    @Autowired
    private ServiceBusSenderClient serviceBusTopicSenderClient;

    @GetMapping("/check")
    public String check() {
        return "Hi Service bus starter";
    }

    @GetMapping("/queue")
    public String sendMessagetoQueue(String message) {
        serviceBusSenderClient.sendMessage(new ServiceBusMessage(message));
        return message;
    }


    @GetMapping("/queue1")
    public String sendMessagetoQueue1(String message) {
        serviceBusSenderClient1.sendMessage(new ServiceBusMessage(message));
        return message;
    }

    @GetMapping("/topic")
    public String sendMessagetotopic(String message) {
        ServiceBusMessage serviceBusMessage = new ServiceBusMessage(message);
        serviceBusMessage.setMessageId("12345");
        serviceBusMessage.setCorrelationId("1234656");
//        serviceBusMessage.setSubject()
//        serviceBusMessage.setContentType()
        Map<String, Object> userProperties = serviceBusMessage.getApplicationProperties();
        userProperties.put("ORDER","CREATED");
        serviceBusTopicSenderClient.sendMessage(serviceBusMessage);
        return message;
    }
}
