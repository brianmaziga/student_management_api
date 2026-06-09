package com.sms.student_management_api.controller;

import com.sms.student_management_api.dto.StudentDTO;
import com.sms.student_management_api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService; // This is the instance variable you must use!

    // 1. POST /api/v1/students
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        // Changed from StudentService to studentService
        StudentDTO savedStudent = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    // 2. GET /api/v1/students
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        // Changed from StudentService to studentService
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // 3. GET /api/v1/students/{id}
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        // Changed from StudentService to studentService
        StudentDTO studentDTO = studentService.getStudentById(id);
        return ResponseEntity.ok(studentDTO);
    }

    // 4. PUT /api/v1/students/{id}
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        // Changed from StudentService to studentService
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    // 5. DELETE /api/v1/students/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        // Changed from StudentService to studentService
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully!");
    }
}
