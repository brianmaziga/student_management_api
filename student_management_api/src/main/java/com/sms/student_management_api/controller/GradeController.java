package com.sms.student_management_api.controller;

import com.sms.student_management_api.dto.GradeDTO;
import com.sms.student_management_api.service.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping
    public ResponseEntity<GradeDTO> assignGrade(@RequestBody GradeDTO gradeDTO) {
        return new ResponseEntity<>(gradeService.assignGrade(gradeDTO), HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeDTO>> getGradesByStudent(@PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(gradeService.getGradesByStudent(studentId), HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<GradeDTO>> getGradesByCourse(@PathVariable("courseId") Long courseId) {
        return new ResponseEntity<>(gradeService.getGradesByCourse(courseId), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GradeDTO> updateGrade(@PathVariable("id") Long id, @RequestBody GradeDTO gradeDTO) {
        return new ResponseEntity<>(gradeService.updateGrade(id, gradeDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable("id") Long id) {
        gradeService.deleteGrade(id);
        return new ResponseEntity<>("Grade deleted successfully.", HttpStatus.OK);
    }
}