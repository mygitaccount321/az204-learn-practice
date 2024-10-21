package com.az204lean.applicationinsight;

import com.microsoft.applicationinsights.attach.ApplicationInsights;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationinsightApplication {

	public static void main(String[] args) {
		ApplicationInsights.attach();
		SpringApplication.run(ApplicationinsightApplication.class, args);
	}

}
