package com.sms.student_management_api.service;

import com.sms.student_management_api.dto.CourseDTO;
import com.sms.student_management_api.entity.Course;
import com.sms.student_management_api.entity.Student;
import com.sms.student_management_api.repository.CourseRepository;
import com.sms.student_management_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EmailService emailService;

    public CourseService(CourseRepository courseRepository,
                         StudentRepository studentRepository,
                         EmailService emailService) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.emailService = emailService;
    }

    // Create course
    public CourseDTO createCourse(CourseDTO dto) {
        Course course = new Course();
        course.setName(dto.getName());
        course.setCode(dto.getCode());
        course.setDescription(dto.getDescription());
        return toDTO(courseRepository.save(course));
    }

    // Get all courses
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Get course by id
    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        return toDTO(course);
    }

    // Update course
    public CourseDTO updateCourse(Long id, CourseDTO dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        if (dto.getName() != null) course.setName(dto.getName());
        if (dto.getCode() != null) course.setCode(dto.getCode());
        if (dto.getDescription() != null) course.setDescription(dto.getDescription());
        return toDTO(courseRepository.save(course));
    }

    // Delete course
    public void deleteCourse(Long id) {
        courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        courseRepository.deleteById(id);
    }

    // Enroll student in course
    public String enrollStudent(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        student.getCourses().add(course);
        studentRepository.save(student);

        // Send enrollment email
        if (student.getEmail() != null) {
            emailService.sendEnrollmentEmail(
                    student.getEmail(),
                    student.getFirstName() + " " + student.getLastName(),
                    course.getName()
            );
        }

        return student.getFirstName() + " enrolled in " + course.getName();
    }

    // Unenroll student from course
    public String unenrollStudent(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        student.getCourses().remove(course);
        studentRepository.save(student);
        return student.getFirstName() + " unenrolled from " + course.getName();
    }

    // Map entity to DTO
    private CourseDTO toDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setCode(course.getCode());
        dto.setDescription(course.getDescription());
        return dto;
    }
}