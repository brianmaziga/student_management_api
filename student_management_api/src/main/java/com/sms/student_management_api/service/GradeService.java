package com.sms.student_management_api.service;

import com.sms.student_management_api.dto.GradeDTO;
import com.sms.student_management_api.entity.Course;
import com.sms.student_management_api.entity.Grade;
import com.sms.student_management_api.entity.Student;
import com.sms.student_management_api.exception.ResourceNotFoundException;
import com.sms.student_management_api.repository.CourseRepository;
import com.sms.student_management_api.repository.GradeRepository;
import com.sms.student_management_api.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService {

    private static final Logger log = LoggerFactory.getLogger(GradeService.class);

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

    public GradeDTO assignGrade(GradeDTO dto) {
        log.info("Assigning grade to student {} for course {}", dto.getStudentId(), dto.getCourseId());
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + dto.getStudentId()));
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + dto.getCourseId()));

        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setCourse(course);
        grade.setScore(dto.getScore());
        grade.setGrade(calculateGrade(dto.getScore()));
        grade.setRemarks(dto.getRemarks());

        Grade saved = gradeRepository.save(grade);
        log.info("Grade assigned: {} for student {}", saved.getGrade(), student.getFirstName());

        if (student.getEmail() != null) {
            emailService.sendGradeEmail(
                    student.getEmail(),
                    student.getFirstName() + " " + student.getLastName(),
                    course.getName(),
                    saved.getScore(),
                    saved.getGrade()
            );
            log.info("Grade email sent to: {}", student.getEmail());
        }

        return toDTO(saved);
    }

    public List<GradeDTO> getGradesByStudent(Long studentId) {
        log.debug("Fetching grades for student id: {}", studentId);
        return gradeRepository.findByStudentId(studentId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<GradeDTO> getGradesByCourse(Long courseId) {
        log.debug("Fetching grades for course id: {}", courseId);
        return gradeRepository.findByCourseId(courseId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public GradeDTO updateGrade(Long id, GradeDTO dto) {
        log.info("Updating grade with id: {}", id);
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with id: " + id));
        if (dto.getScore() != null) {
            grade.setScore(dto.getScore());
            grade.setGrade(calculateGrade(dto.getScore()));
        }
        if (dto.getRemarks() != null) grade.setRemarks(dto.getRemarks());
        return toDTO(gradeRepository.save(grade));
    }

    public void deleteGrade(Long id) {
        log.info("Deleting grade with id: {}", id);
        gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with id: " + id));
        gradeRepository.deleteById(id);
    }

    private String calculateGrade(Double score) {
        if (score >= 70) return "A";
        else if (score >= 60) return "B";
        else if (score >= 50) return "C";
        else if (score >= 40) return "D";
        else return "F";
    }

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