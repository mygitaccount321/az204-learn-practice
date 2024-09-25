package com.az204learn.servicebusstarter.config;

import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Configuration
public class StartQueues {

    @Autowired
    private ServiceBusProcessorClient serviceBusProcessorClient;


    @Autowired
    private ServiceBusProcessorClient serviceBusProcessorClient1;

    @EventListener(ContextStartedEvent.class)
    public void start(ContextStartedEvent event) {
        serviceBusProcessorClient.start();
        serviceBusProcessorClient1.start();
    }
}
