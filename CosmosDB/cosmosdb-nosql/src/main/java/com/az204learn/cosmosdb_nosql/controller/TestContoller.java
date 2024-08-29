package com.az204learn.cosmosdb_nosql.controller;

import com.az204learn.cosmosdb_nosql.models.Student;
import com.az204learn.cosmosdb_nosql.repository.StudentRepository;
import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContoller {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("check")
    public String check() {
        return "Hi Cosmos DB";
    }

    @GetMapping("/student")
    public Student createst() {
        Student st = new Student(1, "2", "Vishwa" );
        studentRepository.save(st);
        return st;
    }

    @GetMapping("/student1")
    public Student createst1() {
        return studentRepository.findById("1").get();
    }
}
