package com.sms.student_management_api.service;

import com.sms.student_management_api.dto.GradeDTO;
import com.sms.student_management_api.entity.Course;
import com.sms.student_management_api.entity.Grade;
import com.sms.student_management_api.entity.Student;
import com.sms.student_management_api.repository.CourseRepository;
import com.sms.student_management_api.repository.GradeRepository;
import com.sms.student_management_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EmailService emailService;

    public GradeService(GradeRepository gradeRepository,
                        StudentRepository studentRepository,
                        CourseRepository courseRepository,
                        EmailService emailService) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.emailService = emailService;
    }

    // Assign grade
    public GradeDTO assignGrade(GradeDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + dto.getCourseId()));

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setCourse(course);
        grade.setScore(dto.getScore());
        grade.setGrade(calculateGrade(dto.getScore()));
        grade.setRemarks(dto.getRemarks());

        Grade saved = gradeRepository.save(grade);

        // Send grade email
        if (student.getEmail() != null) {
            emailService.sendGradeEmail(
                    student.getEmail(),
                    student.getFirstName() + " " + student.getLastName(),
                    course.getName(),
                    saved.getScore(),
                    saved.getGrade()
            );
        }

        return toDTO(saved);
    }

    // Get grades by student
    public List<GradeDTO> getGradesByStudent(Long studentId) {
        return gradeRepository.findByStudentId(studentId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Get grades by course
    public List<GradeDTO> getGradesByCourse(Long courseId) {
        return gradeRepository.findByCourseId(courseId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Update grade
    public GradeDTO updateGrade(Long id, GradeDTO dto) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found with id: " + id));
        if (dto.getScore() != null) {
            grade.setScore(dto.getScore());
            grade.setGrade(calculateGrade(dto.getScore()));
        }
        if (dto.getRemarks() != null) grade.setRemarks(dto.getRemarks());
        return toDTO(gradeRepository.save(grade));
    }

    // Delete grade
    public void deleteGrade(Long id) {
        gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found with id: " + id));
        gradeRepository.deleteById(id);
    }

    // Auto calculate grade letter from score
    private String calculateGrade(Double score) {
        if (score >= 70) return "A";
        else if (score >= 60) return "B";
        else if (score >= 50) return "C";
        else if (score >= 40) return "D";
        else return "F";
    }

    // Map entity to DTO
    private GradeDTO toDTO(Grade grade) {
        GradeDTO dto = new GradeDTO();
        dto.setId(grade.getId());
        dto.setStudentId(grade.getStudent().getId());
        dto.setStudentName(grade.getStudent().getFirstName() + " " + grade.getStudent().getLastName());
        dto.setCourseId(grade.getCourse().getId());
        dto.setCourseName(grade.getCourse().getName());
        dto.setScore(grade.getScore());
        dto.setGrade(grade.getGrade());
        dto.setRemarks(grade.getRemarks());
        return dto;
    }
}