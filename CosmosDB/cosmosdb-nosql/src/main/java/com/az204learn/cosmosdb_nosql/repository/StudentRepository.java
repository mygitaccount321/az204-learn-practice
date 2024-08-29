package com.az204learn.cosmosdb_nosql.repository;

import com.az204learn.cosmosdb_nosql.models.Student;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CosmosRepository<Student, String> {
}
