package com.az204learn.cosmosdb_nosql;

import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCosmosRepositories
public class CosmosdbNosqlQuickstartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CosmosdbNosqlQuickstartApplication.class, args);
	}



}
