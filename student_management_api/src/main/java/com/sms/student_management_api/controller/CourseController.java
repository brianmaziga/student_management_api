package com.sms.student_management_api.controller;

import com.sms.student_management_api.dto.CourseDTO;
import com.sms.student_management_api.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.createCourse(courseDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(courseService.getCourseById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable("id") Long id,
                                                  @RequestBody CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.updateCourse(id, courseDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>("Course deleted successfully.", HttpStatus.OK);
    }

    @PostMapping("/{courseId}/enroll/{studentId}")
    public ResponseEntity<String> enrollStudent(@PathVariable("courseId") Long courseId,
                                                @PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(courseService.enrollStudent(courseId, studentId), HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}/unenroll/{studentId}")
    public ResponseEntity<String> unenrollStudent(@PathVariable("courseId") Long courseId,
                                                  @PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(courseService.unenrollStudent(courseId, studentId), HttpStatus.OK);
    }
}