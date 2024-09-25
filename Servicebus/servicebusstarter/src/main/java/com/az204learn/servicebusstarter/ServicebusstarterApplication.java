package com.az204learn.servicebusstarter;

import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServicebusstarterApplication {


	public static void main(String[] args) {
		SpringApplication.run(ServicebusstarterApplication.class, args).start();
	}

}
