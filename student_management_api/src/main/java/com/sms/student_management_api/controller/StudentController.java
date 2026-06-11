package com.sms.student_management_api.controller;

import com.sms.student_management_api.entity.Student;
import com.sms.student_management_api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // POST: Create
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.saveStudent(student));
    }

    // GET: Read All
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // GET: Read One
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // PUT: Full Update (Replace entire object)
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDetails));
    }

    // PATCH: Partial Update (e.g., just changing email)
    @PatchMapping("/{id}")
    public ResponseEntity<Student> partialUpdate(@PathVariable Long id, @RequestBody Student updates) {
        return ResponseEntity.ok(studentService.partialUpdate(id, updates));
    }

    // DELETE: Remove
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }

    // HEAD: Check if resource exists
    @RequestMapping(method = RequestMethod.HEAD, value = "/{id}")
    public ResponseEntity<?> headStudent(@PathVariable Long id) {
        return studentService.exists(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // OPTIONS: List allowed methods
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> optionsStudents() {
        return ResponseEntity.ok().header("Allow", "GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS").build();
    }
}