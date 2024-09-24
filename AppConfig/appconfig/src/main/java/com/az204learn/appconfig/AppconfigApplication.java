package com.az204learn.appconfig;

import com.az204learn.appconfig.config.MyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MyProperties.class)
public class AppconfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppconfigApplication.class, args);
	}

}
