package com.az204learn.eventhub.config;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azure.messaging.eventhubs.EventProcessorClient;
import com.azure.messaging.eventhubs.EventProcessorClientBuilder;
import com.azure.messaging.eventhubs.checkpointstore.blob.BlobCheckpointStore;
import com.azure.messaging.eventhubs.models.ErrorContext;
import com.azure.messaging.eventhubs.models.EventContext;
import com.azure.storage.blob.BlobContainerAsyncClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfig {
//https://learn.microsoft.com/en-us/azure/developer/java/spring-framework/using-event-hubs-in-spring-applications

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerConfig.class);
    private static final String EVENT_HUB_CONNECTION_STRING = "test";
    private static final String EVENT_HUB_STORAGE_CONNECTION_STRING = "Test";
    private static final String EVENT_HUB_NAME = "az204eventhub";
    private static final String CONSUMER_GROUP = "$Default";
    private static final String STORAGE_CONTAINER_NAME = "mycontainername";

    @Bean
    EventHubClientBuilder eventHubClientBuilder() {
        return new EventHubClientBuilder().
                connectionString(EVENT_HUB_CONNECTION_STRING);
    }

    @Bean
    BlobContainerClientBuilder blobContainerClientBuilder() {
        return new BlobContainerClientBuilder().connectionString(EVENT_HUB_STORAGE_CONNECTION_STRING)
                .containerName(STORAGE_CONTAINER_NAME);
    }

    @Bean
    BlobContainerAsyncClient blobContainerAsyncClient(BlobContainerClientBuilder blobContainerClientBuilder) {
        return blobContainerClientBuilder.buildAsyncClient();
    }

    @Bean
    EventProcessorClientBuilder eventProcessorClientBuilder(BlobContainerAsyncClient blobContainerAsyncClient) {
        return new EventProcessorClientBuilder().connectionString(EVENT_HUB_CONNECTION_STRING)
                .consumerGroup(CONSUMER_GROUP)
                .checkpointStore(new BlobCheckpointStore(blobContainerAsyncClient))
                .processEvent(this::processEvent)
                .processError(this::processError);
    }

    @Bean
    EventHubProducerClient eventHubProducerClient(EventHubClientBuilder eventHubClientBuilder) {
        return eventHubClientBuilder.buildProducerClient();

    }

    @Bean
    EventProcessorClient eventProcessorClient(EventProcessorClientBuilder eventProcessorClientBuilder) {
        return eventProcessorClientBuilder.
                buildEventProcessorClient();
    }

    public void processEvent(EventContext eventContext) {
        LOGGER.info("Processing event from partition {} with sequence number {} with body: {}",
                eventContext.getPartitionContext().getPartitionId(), eventContext.getEventData().getSequenceNumber(),
                eventContext.getEventData().getBodyAsString());
        eventContext.updateCheckpoint();
    }

    public void processError(ErrorContext errorContext) {
        LOGGER.info("Error occurred in partition processor for partition {}, {}",
                errorContext.getPartitionContext().getPartitionId(),
                errorContext.getThrowable());
    }

}
