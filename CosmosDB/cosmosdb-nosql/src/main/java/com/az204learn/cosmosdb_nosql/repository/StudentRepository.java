package com.az204learn.cosmosdb_nosql.repository;

import com.az204learn.cosmosdb_nosql.models.Student;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CosmosRepository<Student, String> {

    @Query("SELECT  * From student s where s.grade= @grade")
    List<Student> getAllStudentInGrade(@Param("grade") String grade);
}
