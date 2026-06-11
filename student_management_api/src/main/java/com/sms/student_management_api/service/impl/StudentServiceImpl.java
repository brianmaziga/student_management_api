package com.sms.student_management_api.service.impl;

import com.sms.student_management_api.entity.Student;
import com.sms.student_management_api.repository.StudentRepository;
import com.sms.student_management_api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    @Override
    public Student updateStudent(Long id, Student details) {
        Student existingStudent = getStudentById(id);
        existingStudent.setFirstName(details.getFirstName());
        existingStudent.setLastName(details.getLastName());
        existingStudent.setEmail(details.getEmail());
        existingStudent.setPhoneNumber(details.getPhoneNumber());
        existingStudent.setCourse(details.getCourse());
        return studentRepository.save(existingStudent);
    }

    @Override
    public Student partialUpdate(Long id, Student updates) {
        Student existing = getStudentById(id);
        if (updates.getFirstName() != null) existing.setFirstName(updates.getFirstName());
        if (updates.getEmail() != null) existing.setEmail(updates.getEmail());
        // Add more fields as needed
        return studentRepository.save(existing);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public boolean exists(Long id) {
        return studentRepository.existsById(id);
    }
}