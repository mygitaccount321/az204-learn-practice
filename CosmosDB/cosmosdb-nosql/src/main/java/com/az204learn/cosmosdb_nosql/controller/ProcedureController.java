package com.az204learn.cosmosdb_nosql.controller;

import com.az204learn.cosmosdb_nosql.models.Student;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosStoredProcedure;
import com.azure.cosmos.models.CosmosStoredProcedureRequestOptions;
import com.azure.cosmos.models.CosmosStoredProcedureResponse;
import com.azure.cosmos.models.PartitionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sp")
public class ProcedureController {
    @Autowired
    private CosmosContainer cosmosContainer;

    @GetMapping("/hellostoreprocedure")
    public String helloStoreProcedure(String param) {
        try {

//            CosmosContainer cosmosContainer = cosmosClient
//                    .getDatabase("college")
//                    .getContainer("student");
            CosmosStoredProcedure sproc = cosmosContainer
                    .getScripts()
                    .getStoredProcedure("hellowordsp");
            CosmosStoredProcedureRequestOptions options = new CosmosStoredProcedureRequestOptions();
            options.setPartitionKey(new PartitionKey("test"));
            CosmosStoredProcedureResponse response = sproc.execute(
                    List.of(param),
                    options
            );
            System.out.println("Stored procedure executed with response: " + response.getResponseAsString());
            return response.getResponseAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
       return  null;
    }

    @PostMapping("/student")
    public String createItems(@RequestBody Student student) {
        student.setId(UUID.randomUUID().toString());
        try {

//            CosmosContainer cosmosContainer = cosmosClient
//                    .getDatabase("college")
//                    .getContainer("student");
            CosmosStoredProcedure sproc = cosmosContainer
                    .getScripts()
                    .getStoredProcedure("createitems");
            CosmosStoredProcedureRequestOptions options = new CosmosStoredProcedureRequestOptions();
            options.setPartitionKey(new PartitionKey(student.getName()));
            CosmosStoredProcedureResponse response = sproc.execute(
                    List.of(List.of(student)),
                    options
            );
            System.out.println("Stored procedure executed with response: " + response.getResponseAsString());
            return response.getResponseAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
