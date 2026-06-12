package com.sms.student_management_api.service;

import com.sms.student_management_api.dto.StudentDTO;
import com.sms.student_management_api.entity.Student;
import com.sms.student_management_api.exception.ResourceNotFoundException;
import com.sms.student_management_api.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentDTO saveStudent(StudentDTO dto) {
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setCourse(dto.getCourse());
        return toDTO(studentRepository.save(student));
    }

    public Page<StudentDTO> getStudents(String keyword, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        if (keyword != null && !keyword.isEmpty()) {
            return studentRepository
                    .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrCourseContainingIgnoreCase(
                            keyword, keyword, keyword, pageable)
                    .map(this::toDTO);
        }
        return studentRepository.findAll(pageable).map(this::toDTO);
    }

    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return toDTO(student);
    }

    public StudentDTO partialUpdate(Long id, StudentDTO updates) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        if (updates.getFirstName() != null) student.setFirstName(updates.getFirstName());
        if (updates.getLastName() != null) student.setLastName(updates.getLastName());
        if (updates.getEmail() != null) student.setEmail(updates.getEmail());
        if (updates.getPhoneNumber() != null) student.setPhoneNumber(updates.getPhoneNumber());
        if (updates.getCourse() != null) student.setCourse(updates.getCourse());
        return toDTO(studentRepository.save(student));
    }

    public void deleteStudent(Long id) {
        studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        studentRepository.deleteById(id);
    }

    private StudentDTO toDTO(Student student) {
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