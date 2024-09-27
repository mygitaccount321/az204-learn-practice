package com.az204learn.eventhub.contoller;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubConsumerClient;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


@RestController
@Slf4j
public class TestController {

    @Autowired
    private EventHubProducerClient producerClient;

    @GetMapping("/check")
    public String check() {
        return "Hi Event hub";
    }

    @GetMapping("/sendmsg")
    public String sendmsg(String message) {
        log.info("Event Hub producer client created");
        producerClient.send(Arrays.asList(new EventData(message)));
        log.info("Sent message to Event Hub");
        return message;
    }


}
