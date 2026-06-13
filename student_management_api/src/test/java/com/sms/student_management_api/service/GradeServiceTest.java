package com.sms.student_management_api.service;

import com.sms.student_management_api.dto.GradeDTO;
import com.sms.student_management_api.entity.Course;
import com.sms.student_management_api.entity.Grade;
import com.sms.student_management_api.entity.Student;
import com.sms.student_management_api.exception.ResourceNotFoundException;
import com.sms.student_management_api.repository.CourseRepository;
import com.sms.student_management_api.repository.GradeRepository;
import com.sms.student_management_api.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GradeServiceTest {

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private GradeService gradeService;

    private Student student;
    private Course course;
    private Grade grade;
    private GradeDTO gradeDTO;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setFirstName("Brian");
        student.setLastName("Kent");
        student.setEmail("brian.k@example.com");

        course = new Course();
        course.setId(1L);
        course.setName("Computer Science");
        course.setCode("CS101");

        grade = new Grade();
        grade.setId(1L);
        grade.setStudent(student);
        grade.setCourse(course);
        grade.setScore(85.0);
        grade.setGrade("A");
        grade.setRemarks("Excellent");

        gradeDTO = new GradeDTO();
        gradeDTO.setStudentId(1L);
        gradeDTO.setCourseId(1L);
        gradeDTO.setScore(85.0);
        gradeDTO.setRemarks("Excellent");
    }

    @Test
    void assignGrade_ShouldReturnGrade_WhenValid() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(gradeRepository.save(any(Grade.class))).thenReturn(grade);

        GradeDTO result = gradeService.assignGrade(gradeDTO);

        assertNotNull(result);
        assertEquals(85.0, result.getScore());
        assertEquals("A", result.getGrade());
        verify(gradeRepository, times(1)).save(any(Grade.class));
    }

    @Test
    void assignGrade_ShouldThrowException_WhenStudentNotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        gradeDTO.setStudentId(999L);

        assertThrows(ResourceNotFoundException.class, () -> {
            gradeService.assignGrade(gradeDTO);
        });
    }

    @Test
    void assignGrade_ShouldThrowException_WhenCourseNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        gradeDTO.setCourseId(999L);

        assertThrows(ResourceNotFoundException.class, () -> {
            gradeService.assignGrade(gradeDTO);
        });
    }

    @Test
    void deleteGrade_ShouldDeleteSuccessfully_WhenExists() {
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        doNothing().when(gradeRepository).deleteById(1L);

        assertDoesNotThrow(() -> gradeService.deleteGrade(1L));
        verify(gradeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteGrade_ShouldThrowException_WhenNotFound() {
        when(gradeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            gradeService.deleteGrade(999L);
        });
    }
}
