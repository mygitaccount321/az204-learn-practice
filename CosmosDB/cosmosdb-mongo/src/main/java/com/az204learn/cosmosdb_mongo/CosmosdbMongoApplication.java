package com.az204learn.cosmosdb_mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EntityScan({"com.az204learn.cosmosdb_mongo.model"})
@EnableMongoRepositories({"com.az204learn.cosmosdb_mongo.repository"})
public class CosmosdbMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CosmosdbMongoApplication.class, args);
	}

}
