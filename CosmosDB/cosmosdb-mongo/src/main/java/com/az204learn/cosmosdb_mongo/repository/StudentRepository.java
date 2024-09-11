package com.az204learn.cosmosdb_mongo.repository;


import com.az204learn.cosmosdb_mongo.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

}
