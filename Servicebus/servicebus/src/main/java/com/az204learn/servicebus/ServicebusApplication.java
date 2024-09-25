package com.az204learn.servicebus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class ServicebusApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicebusApplication.class, args);
	}

}
