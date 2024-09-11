package com.az204learn.cosmosdb_nosql.controller;

import com.az204learn.cosmosdb_nosql.models.Student;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.PartitionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trigger")
public class TriggerController {

    @Autowired
    private CosmosContainer cosmosContainer;

    @PostMapping("/student")
    public Student createItemsWithPreTrigger(@RequestBody Student student) {
        student.setId(UUID.randomUUID().toString());
        try {
            CosmosItemRequestOptions options = new CosmosItemRequestOptions();
            options.setPreTriggerInclude(
                    List.of("validateToDoItemTimestamp")
            );
            CosmosItemResponse<Student> response = cosmosContainer.createItem(student, options);
            System.out.println("Stored procedure executed with response: " + response.getItem());
            return response.getItem();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/student-post-trigger")
    public Student createItemsWithPostTrigger(@RequestBody Student student) {
        student.setId(UUID.randomUUID().toString());
        try {
            CosmosItemRequestOptions options = new CosmosItemRequestOptions();
            options.setPostTriggerInclude(
                    List.of("updateProperties")
            );
            CosmosItemResponse<Student> response = cosmosContainer.createItem(student, new PartitionKey(student.getName()), options);
            return student;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
