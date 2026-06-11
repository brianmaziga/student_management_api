package com.sms.student_management_api.controller;

import com.sms.student_management_api.dto.StudentDTO;
import com.sms.student_management_api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 1. Get students with keyword filtering
    @GetMapping("/search")
    public ResponseEntity<Page<StudentDTO>> getStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword
    ) {
        return ResponseEntity.ok(studentService.getStudents(keyword, page, size));
    }

    // 2. Get all students with clear parameter naming bindings for Swagger UI
    @GetMapping
    public ResponseEntity<?> getAllStudents(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(studentService.getStudents("",page, size));
    }
}