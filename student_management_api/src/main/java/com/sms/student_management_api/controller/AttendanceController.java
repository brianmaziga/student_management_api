package com.sms.student_management_api.controller;

import com.sms.student_management_api.dto.AttendanceDTO;
import com.sms.student_management_api.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@Tag(name = "Attendance Management", description = "Endpoints for tracking student attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    @Operation(summary = "Mark attendance", description = "Marks attendance for a student in a specific course")
    public ResponseEntity<AttendanceDTO> markAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        return new ResponseEntity<>(attendanceService.markAttendance(attendanceDTO), HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get attendance by student", description = "Returns all attendance records for a specific student")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByStudent(@PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(attendanceService.getAttendanceByStudent(studentId), HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get attendance by course", description = "Returns all attendance records for a specific course")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByCourse(@PathVariable("courseId") Long courseId) {
        return new ResponseEntity<>(attendanceService.getAttendanceByCourse(courseId), HttpStatus.OK);
    }

    @GetMapping("/date/{date}")
    @Operation(summary = "Get attendance by date", description = "Returns all attendance records for a specific date")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByDate(
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return new ResponseEntity<>(attendanceService.getAttendanceByDate(date), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update attendance", description = "Updates an existing attendance record")
    public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable("id") Long id,
                                                          @RequestBody AttendanceDTO attendanceDTO) {
        return new ResponseEntity<>(attendanceService.updateAttendance(id, attendanceDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete attendance", description = "Permanently deletes an attendance record")
    public ResponseEntity<String> deleteAttendance(@PathVariable("id") Long id) {
        attendanceService.deleteAttendance(id);
        return new ResponseEntity<>("Attendance record deleted successfully.", HttpStatus.OK);
    }
}