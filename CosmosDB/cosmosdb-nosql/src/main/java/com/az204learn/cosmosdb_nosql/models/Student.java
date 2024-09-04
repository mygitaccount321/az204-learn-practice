package com.az204learn.cosmosdb_nosql.models;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.CosmosUniqueKey;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

@Container(containerName = "student")
@AllArgsConstructor
@Data
public class Student {
    @Id
    private String id;
    @PartitionKey
    private String grade;
    private String name;
    public Integer ttl;
}
