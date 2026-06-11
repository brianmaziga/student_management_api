package com.sms.student_management_api.service.impl;

import com.sms.student_management_api.dto.StudentDTO;
import com.sms.student_management_api.entity.Student;
import com.sms.student_management_api.repository.StudentRepository;
import com.sms.student_management_api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentDTO saveStudent(StudentDTO studentDTO) {
        // Validation check for duplicate email
        if (studentRepository.existsByEmail(studentDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists!");
        }

        // Convert DTO to Entity
        Student student = new Student();
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setCourse(studentDTO.getCourse());

        // Save Entity to Database
        Student savedStudent = studentRepository.save(student);
        return convertToDto(savedStudent);
    }

    @Override
    public Page<StudentDTO> getStudents(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentPage;

        // Perform text filtering across fields if keyword is provided
        if (keyword != null && !keyword.trim().isEmpty()) {
            studentPage = studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrCourseContainingIgnoreCase(
                    keyword, keyword, keyword, pageable);
        } else {
            studentPage = studentRepository.findAll(pageable);
        }

        // Map the internal Entity page to a clean DTO page
        return studentPage.map(this::convertToDto);
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + id));
        return convertToDto(student);
    }

    @Override
    public StudentDTO partialUpdate(Long id, StudentDTO updates) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + id));

        // Dynamically update fields only if they are passed in the request body
        if (updates.getFirstName() != null && !updates.getFirstName().trim().isEmpty()) {
            student.setFirstName(updates.getFirstName());
        }
        if (updates.getLastName() != null && !updates.getLastName().trim().isEmpty()) {
            student.setLastName(updates.getLastName());
        }
        if (updates.getPhoneNumber() != null && !updates.getPhoneNumber().trim().isEmpty()) {
            student.setPhoneNumber(updates.getPhoneNumber());
        }
        if (updates.getCourse() != null && !updates.getCourse().trim().isEmpty()) {
            student.setCourse(updates.getCourse());
        }

        // Handle sensitive email updates securely
        if (updates.getEmail() != null && !updates.getEmail().trim().isEmpty()) {
            if (!updates.getEmail().equals(student.getEmail()) && studentRepository.existsByEmail(updates.getEmail())) {
                throw new IllegalArgumentException("Email already taken by another student!");
            }
            student.setEmail(updates.getEmail());
        }

        Student updatedStudent = studentRepository.save(student);
        return convertToDto(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot delete. Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }

    // Single source of truth for Entity-to-DTO translation
    private StudentDTO convertToDto(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setCourse(student.getCourse());
        dto.setCreatedAt(student.getCreatedAt());
        return dto;
    }
}