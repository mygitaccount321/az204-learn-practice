package com.az204learn.cosmosdb_nosql;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.CosmosDatabaseResponse;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCosmosRepositories
public class CosmosdbNosqlQuickstartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CosmosdbNosqlQuickstartApplication.class, args);
	}

	@Bean
	public CosmosDatabase cosmosDatabase(CosmosClient cosmosClient) {
		return cosmosClient.getDatabase("college");
	}

	@Bean
	public CosmosContainer cosmosContainer(CosmosDatabase cosmosDatabase) {
		return cosmosDatabase.getContainer("student");
	}
}
