package com.az204learn.cosmosdb_nosql.controller;

import com.az204learn.cosmosdb_nosql.models.CostWithTax;
import com.az204learn.cosmosdb_nosql.repository.StudentRepository;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/udf")
public class UserDfFunctionController {

    @Autowired
    private CosmosContainer cosmosContainer;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public List<CostWithTax> getAllStudentInGrade() {
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();
        CosmosPagedIterable<CostWithTax> iterable = cosmosContainer.queryItems(
                "SELECT t.cost, t.cost AS origionalCost, udf.tax(t.cost) AS costWithTax FROM student t",
                options,
                CostWithTax.class);
        return iterable.stream().toList();
    }
}
