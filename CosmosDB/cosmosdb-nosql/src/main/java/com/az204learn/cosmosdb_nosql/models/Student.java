package com.az204learn.cosmosdb_nosql.models;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Container(containerName = "student")
@AllArgsConstructor
@Data
public class Student {
    @Id
    private String id;
    private String grade;
    @PartitionKey
    private String name;
    private String timestamp;
    private String response;
    private Integer cost;
    public Integer ttl;
}
