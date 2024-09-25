package com.az204learn.servicebusstarter.configTopic;

import com.az204learn.servicebusstarter.config.ServiceBusQueueClientConfiguration;
import com.azure.messaging.servicebus.*;
import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClient;
import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClientBuilder;
import com.azure.messaging.servicebus.administration.models.CreateRuleOptions;
import com.azure.messaging.servicebus.administration.models.CreateSubscriptionOptions;
import com.azure.messaging.servicebus.administration.models.SqlRuleFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBusTopicClientConfiguration {

    //https://learn.microsoft.com/en-us/azure/developer/java/spring-framework/using-service-bus-in-spring-applications
    private static final String SERVICE_BUS_CONNECTION_STRING_TOPIC = "test";
    private static final String TOPIC = "mytopic";
    private static final String SUB_S4 = "S4";

    @Bean
    ServiceBusClientBuilder serviceBusTopicClientBuilder() {
        return new ServiceBusClientBuilder()
                .connectionString(SERVICE_BUS_CONNECTION_STRING_TOPIC);
    }

//    @Bean
//    ServiceBusClientBuilder serviceBusTopicClientBuilder111() {
//        ServiceBusAdministrationClient adminClient = new ServiceBusAdministrationClientBuilder().connectionString(SERVICE_BUS_CONNECTION_STRING_TOPIC).buildClient();
//        SqlRuleFilter filter = new SqlRuleFilter("status = 'active'");
////        CreateSubscriptionOptions options = new CreateSubscriptionOptions();
//        CreateRuleOptions createRuleOptions = new CreateRuleOptions() ;
//        createRuleOptions.setFilter(filter);
////        CreateSubscriptionOptions options1 = new CreateSubscriptionOptions()
////                .addRule("activeMessagesFilter", filter);
//
//        adminClient.createSubscription("","");
//        adminClient.createRule("","","",createRuleOptions);
//
//
//        return null;
//    }

    @Bean
    ServiceBusSenderClient serviceBusTopicSenderClient(ServiceBusClientBuilder serviceBusTopicClientBuilder) {
        return serviceBusTopicClientBuilder
                .sender()
                .topicName(TOPIC)
                .buildClient();
    }

    @Bean
    ServiceBusProcessorClient serviceBusProcessorTopicClient(ServiceBusClientBuilder serviceBusTopicClientBuilder) {
        return serviceBusTopicClientBuilder.processor()
                .topicName(TOPIC)
                .subscriptionName(SUB_S4)
                .processMessage(ServiceBusTopicClientConfiguration::processMessage)
                .processError(ServiceBusTopicClientConfiguration::processError)
                .buildProcessorClient();
    }

    private static void processMessage(ServiceBusReceivedMessageContext context) {
        ServiceBusReceivedMessage message = context.getMessage();
        System.out.printf("%s Processing message. Id: %s, Sequence #: %s. Contents: %s%n",SUB_S4,
                message.getMessageId(), message.getSequenceNumber(), message.getBody());
    }

    private static void processError(ServiceBusErrorContext context) {
        System.out.printf("Error when receiving messages from namespace: '%s'. Entity: '%s'%n",
                context.getFullyQualifiedNamespace(), context.getEntityPath());
    }

}
