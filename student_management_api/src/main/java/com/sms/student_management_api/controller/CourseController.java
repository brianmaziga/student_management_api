package com.sms.student_management_api.controller;

import com.sms.student_management_api.dto.CourseDTO;
import com.sms.student_management_api.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Course Management", description = "Endpoints for managing courses and enrollment")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @Operation(summary = "Create a new course", description = "Adds a new course to the system")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.createCourse(courseDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all courses", description = "Returns a list of all available courses")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID", description = "Returns a single course by its ID")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(courseService.getCourseById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update course", description = "Partially updates a course record")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable("id") Long id,
                                                  @RequestBody CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.updateCourse(id, courseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course", description = "Permanently deletes a course")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>("Course deleted successfully.", HttpStatus.OK);
    }

    @PostMapping("/{courseId}/enroll/{studentId}")
    @Operation(summary = "Enroll student in course", description = "Enrolls a student in a specific course and sends confirmation email")
    public ResponseEntity<String> enrollStudent(@PathVariable("courseId") Long courseId,
                                                @PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(courseService.enrollStudent(courseId, studentId), HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}/unenroll/{studentId}")
    @Operation(summary = "Unenroll student from course", description = "Removes a student from a specific course")
    public ResponseEntity<String> unenrollStudent(@PathVariable("courseId") Long courseId,
                                                  @PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(courseService.unenrollStudent(courseId, studentId), HttpStatus.OK);
    }
}