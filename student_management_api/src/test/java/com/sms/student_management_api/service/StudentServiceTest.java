package com.sms.student_management_api.service;

import com.sms.student_management_api.dto.StudentDTO;
import com.sms.student_management_api.entity.Student;
import com.sms.student_management_api.exception.ResourceNotFoundException;
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
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;
    private StudentDTO studentDTO;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setFirstName("Brian");
        student.setLastName("Kent");
        student.setEmail("brian.k@example.com");
        student.setPhoneNumber("0711111111");
        student.setCourse("Computer Science");

        studentDTO = new StudentDTO();
        studentDTO.setFirstName("Brian");
        studentDTO.setLastName("Kent");
        studentDTO.setEmail("brian.k@example.com");
        studentDTO.setPhoneNumber("0711111111");
        studentDTO.setCourse("Computer Science");
    }

    @Test
    void saveStudent_ShouldReturnSavedStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentDTO result = studentService.saveStudent(studentDTO);

        assertNotNull(result);
        assertEquals("Brian", result.getFirstName());
        assertEquals("Kent", result.getLastName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void getStudentById_ShouldReturnStudent_WhenExists() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        StudentDTO result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals("Brian", result.getFirstName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void getStudentById_ShouldThrowException_WhenNotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.getStudentById(999L);
        });
        verify(studentRepository, times(1)).findById(999L);
    }

    @Test
    void deleteStudent_ShouldDeleteSuccessfully_WhenExists() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).deleteById(1L);

        assertDoesNotThrow(() -> studentService.deleteStudent(1L));
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteStudent_ShouldThrowException_WhenNotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            studentService.deleteStudent(999L);
        });
    }

    @Test
    void partialUpdate_ShouldUpdateFields_WhenExists() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentDTO updates = new StudentDTO();
        updates.setEmail("newemail@example.com");

        StudentDTO result = studentService.partialUpdate(1L, updates);

        assertNotNull(result);
        verify(studentRepository, times(1)).save(any(Student.class));
    }
}
