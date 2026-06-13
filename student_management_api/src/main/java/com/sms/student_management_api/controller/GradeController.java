package com.sms.student_management_api.controller;

import com.sms.student_management_api.dto.GradeDTO;
import com.sms.student_management_api.service.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@Tag(name = "Grade Management", description = "Endpoints for managing student grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping
    @Operation(summary = "Assign grade", description = "Assigns a grade to a student for a specific course")
    public ResponseEntity<GradeDTO> assignGrade(@RequestBody GradeDTO gradeDTO) {
        return new ResponseEntity<>(gradeService.assignGrade(gradeDTO), HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get grades by student", description = "Returns all grades for a specific student")
    public ResponseEntity<List<GradeDTO>> getGradesByStudent(@PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(gradeService.getGradesByStudent(studentId), HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get grades by course", description = "Returns all grades for a specific course")
    public ResponseEntity<List<GradeDTO>> getGradesByCourse(@PathVariable("courseId") Long courseId) {
        return new ResponseEntity<>(gradeService.getGradesByCourse(courseId), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update grade", description = "Updates an existing grade record")
    public ResponseEntity<GradeDTO> updateGrade(@PathVariable("id") Long id,
                                                @RequestBody GradeDTO gradeDTO) {
        return new ResponseEntity<>(gradeService.updateGrade(id, gradeDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete grade", description = "Permanently deletes a grade record")
    public ResponseEntity<String> deleteGrade(@PathVariable("id") Long id) {
        gradeService.deleteGrade(id);
        return new ResponseEntity<>("Grade deleted successfully.", HttpStatus.OK);
    }
}