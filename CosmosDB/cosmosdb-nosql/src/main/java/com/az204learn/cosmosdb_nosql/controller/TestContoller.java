package com.az204learn.cosmosdb_nosql.controller;

import com.az204learn.cosmosdb_nosql.models.Student;
import com.az204learn.cosmosdb_nosql.repository.StudentRepository;
import com.sun.jna.platform.win32.Guid;
import jakarta.websocket.server.PathParam;
import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TestContoller {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("check")
    public String check() {
        return "Hi Cosmos DB";
    }

    @PostMapping("/student")
    public Student createst(@RequestBody Student student) {
        student.setId(UUID.randomUUID().toString());
        studentRepository.save(student);
        return student;
    }

    @PutMapping("/student")
    public Student createstionUpdate(@RequestBody Student student) {
        studentRepository.save(student);
        return student;
    }

    @GetMapping("/student/{id}")
    public Student createst1(@PathVariable String id) {
        return studentRepository.findById(id).get();
    }

    @GetMapping("/students")
    public List<Student> getAllStudentInGrade(@PathParam("grade") String grade) {
        return studentRepository.getAllStudentInGrade(grade);
    }
}
