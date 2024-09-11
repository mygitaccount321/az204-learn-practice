package com.az204learn.cosmosdb_mongo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "student")
@AllArgsConstructor
@Data
public class Student {
    @Id
    private String id;
    private String grade;
    private String name;
    public Integer ttl;
}
