package com.sms.student_management_api.controller;

import com.sms.student_management_api.dto.StudentDTO;
import com.sms.student_management_api.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Management", description = "Endpoints for managing students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @Operation(summary = "Create a new student", description = "Adds a new student record to the system")
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        return new ResponseEntity<>(studentService.saveStudent(studentDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all students", description = "Returns a paginated list of all students")
    public ResponseEntity<Page<StudentDTO>> getAllStudents(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(studentService.getStudents("", page, size));
    }

    @GetMapping("/search")
    @Operation(summary = "Search students", description = "Search students by name or course")
    public ResponseEntity<Page<StudentDTO>> searchStudents(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(studentService.getStudents(keyword, page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", description = "Returns a single student by their ID")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update student", description = "Partially updates a student record")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable("id") Long id,
                                                    @RequestBody StudentDTO updates) {
        return new ResponseEntity<>(studentService.partialUpdate(id, updates), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student", description = "Permanently deletes a student record")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>("Student record deleted successfully.", HttpStatus.OK);
    }
}