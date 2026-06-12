package com.sms.student_management_api.service;

import com.sms.student_management_api.dto.AttendanceDTO;
import com.sms.student_management_api.entity.Attendance;
import com.sms.student_management_api.entity.Course;
import com.sms.student_management_api.entity.Student;
import com.sms.student_management_api.repository.AttendanceRepository;
import com.sms.student_management_api.repository.CourseRepository;
import com.sms.student_management_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // Mark attendance
    public AttendanceDTO markAttendance(AttendanceDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + dto.getCourseId()));

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setCourse(course);
        attendance.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());
        attendance.setStatus(Attendance.AttendanceStatus.valueOf(dto.getStatus().toUpperCase()));
        attendance.setRemarks(dto.getRemarks());

        return toDTO(attendanceRepository.save(attendance));
    }

    // Get attendance by student
    public List<AttendanceDTO> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Get attendance by course
    public List<AttendanceDTO> getAttendanceByCourse(Long courseId) {
        return attendanceRepository.findByCourseId(courseId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Get attendance by date
    public List<AttendanceDTO> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Update attendance
    public AttendanceDTO updateAttendance(Long id, AttendanceDTO dto) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with id: " + id));
        if (dto.getStatus() != null)
            attendance.setStatus(Attendance.AttendanceStatus.valueOf(dto.getStatus().toUpperCase()));
        if (dto.getRemarks() != null)
            attendance.setRemarks(dto.getRemarks());
        if (dto.getDate() != null)
            attendance.setDate(dto.getDate());
        return toDTO(attendanceRepository.save(attendance));
    }

    // Delete attendance
    public void deleteAttendance(Long id) {
        attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with id: " + id));
        attendanceRepository.deleteById(id);
    }

    // Map entity to DTO
    private AttendanceDTO toDTO(Attendance attendance) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setStudentId(attendance.getStudent().getId());
        dto.setStudentName(attendance.getStudent().getFirstName() + " " + attendance.getStudent().getLastName());
        dto.setCourseId(attendance.getCourse().getId());
        dto.setCourseName(attendance.getCourse().getName());
        dto.setDate(attendance.getDate());
        dto.setStatus(attendance.getStatus().name());
        dto.setRemarks(attendance.getRemarks());
        return dto;
    }
}
