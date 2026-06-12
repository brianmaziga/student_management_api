package com.sms.student_management_api.controller;

import com.sms.student_management_api.dto.AttendanceDTO;
import com.sms.student_management_api.service.AttendanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<AttendanceDTO> markAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        return new ResponseEntity<>(attendanceService.markAttendance(attendanceDTO), HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByStudent(@PathVariable("studentId") Long studentId) {
        return new ResponseEntity<>(attendanceService.getAttendanceByStudent(studentId), HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByCourse(@PathVariable("courseId") Long courseId) {
        return new ResponseEntity<>(attendanceService.getAttendanceByCourse(courseId), HttpStatus.OK);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByDate(
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return new ResponseEntity<>(attendanceService.getAttendanceByDate(date), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable("id") Long id,
                                                          @RequestBody AttendanceDTO attendanceDTO) {
        return new ResponseEntity<>(attendanceService.updateAttendance(id, attendanceDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttendance(@PathVariable("id") Long id) {
        attendanceService.deleteAttendance(id);
        return new ResponseEntity<>("Attendance record deleted successfully.", HttpStatus.OK);
    }
}
