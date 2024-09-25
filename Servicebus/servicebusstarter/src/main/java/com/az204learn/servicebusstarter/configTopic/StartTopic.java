package com.az204learn.servicebusstarter.configTopic;

import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;

@Configuration
public class StartTopic {

    @Autowired
    private ServiceBusProcessorClient serviceBusProcessorTopicClient;

    @EventListener(ContextStartedEvent.class)
    public void start(ContextStartedEvent event) {
        serviceBusProcessorTopicClient.start();
    }
}
