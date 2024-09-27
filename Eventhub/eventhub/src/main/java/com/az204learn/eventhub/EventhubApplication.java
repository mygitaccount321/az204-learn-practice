package com.az204learn.eventhub;

import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azure.messaging.eventhubs.EventProcessorClient;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class EventhubApplication {

	@Autowired
	private EventProcessorClient eventProcessorClient;

	@Autowired
	private EventHubProducerClient eventHubProducerClient;

	public static void main(String[] args) {
		SpringApplication.run(EventhubApplication.class, args).start();
	}

	@EventListener(ApplicationStartedEvent.class)
	public void started() {
		eventProcessorClient.start();
	}


	@PreDestroy
	public void onDestroy() {
		System.out.println("Application is shutting down. Performing cleanup...");
		eventHubProducerClient.close();
		eventProcessorClient.stop();
	}
}
