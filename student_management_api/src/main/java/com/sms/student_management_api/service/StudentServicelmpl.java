package com.sms.student_management_api.service;

import com.sms.student_management_api.dto.StudentDTO;
import com.sms.student_management_api.entity.Student;
import com.sms.student_management_api.exception.ResourceNotFoundException;
import com.sms.student_management_api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServicelmpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        if (studentRepository.findByEmail(studentDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists!");
        }
        Student student = mapToEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return mapToDTO(savedStudent);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return mapToDTO(student);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setCourse(studentDTO.getCourse());

        return mapToDTO(studentRepository.save(student));
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        studentRepository.delete(student);
    }

    private StudentDTO mapToDTO(Student student) {
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

    private Student mapToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setCourse(dto.getCourse());
        return student;
    }
}
