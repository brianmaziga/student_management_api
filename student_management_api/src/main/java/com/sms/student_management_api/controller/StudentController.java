package com.sms.student_management_api.controller;

import com.sms.student_management_api.dto.StudentDTO;
import com.sms.student_management_api.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    // Constructor injection
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // CREATE: Post standard student records with input validation rules enforced
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        return new ResponseEntity<>(studentService.saveStudent(studentDTO), HttpStatus.CREATED);
    }

    // READ (ALL / SEARCH): Fetch paginated lists, optional filtering via text keywords
    @GetMapping
    public ResponseEntity<Page<StudentDTO>> getStudents(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(studentService.getStudents(keyword, page, size), HttpStatus.OK);
    }

    // READ (SINGLE): Look up records via unique system path parameters
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }

    // UPDATE: Execute targeted property corrections without full object requirements
    @PatchMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable("id") Long id, @RequestBody StudentDTO updates) {
        return new ResponseEntity<>(studentService.partialUpdate(id, updates), HttpStatus.OK);
    }

    // DELETE: Discard structural records directly out of the database tier
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>("Student record deleted successfully.", HttpStatus.OK);
    }
}