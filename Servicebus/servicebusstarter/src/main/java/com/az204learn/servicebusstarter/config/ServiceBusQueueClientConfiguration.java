package com.az204learn.servicebusstarter.config;

import com.azure.messaging.servicebus.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBusQueueClientConfiguration {

    //https://learn.microsoft.com/en-us/azure/developer/java/spring-framework/using-service-bus-in-spring-applications
    private static final String SERVICE_BUS_CONNECTION_STRING_MYQUEUE = "test";
    private static final String SERVICE_BUS_CONNECTION_STRING_MYQUEUE1 = "test";
    private static final String QUEUE_NAME = "myqueue";
    private static final String QUEUE_NAME1 = "myqueue1";

    @Bean
    ServiceBusClientBuilder serviceBusClientBuilder() {
        return new ServiceBusClientBuilder()
                .connectionString(SERVICE_BUS_CONNECTION_STRING_MYQUEUE);
    }

    @Bean
    ServiceBusClientBuilder serviceBusClientBuilder1() {
        return new ServiceBusClientBuilder()
                .connectionString(SERVICE_BUS_CONNECTION_STRING_MYQUEUE1);
    }

    @Bean
    ServiceBusSenderClient serviceBusSenderClient(ServiceBusClientBuilder serviceBusClientBuilder) {
        return serviceBusClientBuilder
                .sender()
                .queueName(QUEUE_NAME)
                .buildClient();
    }

    @Bean
    ServiceBusSenderClient serviceBusSenderClient1(ServiceBusClientBuilder serviceBusClientBuilder1) {
        return serviceBusClientBuilder1
                .sender()
                .queueName(QUEUE_NAME1)
                .buildClient();
    }

    @Bean
    ServiceBusProcessorClient serviceBusProcessorClient(ServiceBusClientBuilder serviceBusClientBuilder) {
        return serviceBusClientBuilder.processor()
                .queueName(QUEUE_NAME)
                .processMessage(ServiceBusQueueClientConfiguration::processMessage)
                .processError(ServiceBusQueueClientConfiguration::processError)
                .buildProcessorClient();
    }

    private static void processMessage(ServiceBusReceivedMessageContext context) {
        ServiceBusReceivedMessage message = context.getMessage();
        System.out.printf("%s Processing message. Id: %s, Sequence #: %s. Contents: %s%n",QUEUE_NAME,
                message.getMessageId(), message.getSequenceNumber(), message.getBody());
    }

    private static void processError(ServiceBusErrorContext context) {
        System.out.printf("Error when receiving messages from namespace: '%s'. Entity: '%s'%n",
                context.getFullyQualifiedNamespace(), context.getEntityPath());
    }

    @Bean
    ServiceBusProcessorClient serviceBusProcessorClient1(ServiceBusClientBuilder serviceBusClientBuilder1) {
        return serviceBusClientBuilder1.processor()
                .queueName(QUEUE_NAME1)
                .processMessage(ServiceBusQueueClientConfiguration::processMessage1)
                .processError(ServiceBusQueueClientConfiguration::processError1)
                .buildProcessorClient();
    }



    private static void processMessage1(ServiceBusReceivedMessageContext context) {
        ServiceBusReceivedMessage message = context.getMessage();
        System.out.printf("%s Processing message. Id: %s, Sequence #: %s. Contents: %s%n",QUEUE_NAME1,
                message.getMessageId(), message.getSequenceNumber(), message.getBody());
    }

    private static void processError1(ServiceBusErrorContext context) {
        System.out.printf("Error when receiving messages from namespace: '%s'. Entity: '%s'%n",
                context.getFullyQualifiedNamespace(), context.getEntityPath());
    }
}
