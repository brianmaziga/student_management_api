package com.sms.student_management_api.service;

import com.sms.student_management_api.entity.Student;
import java.util.List;

public interface StudentService {
    Student saveStudent(Student student);
    List<Student> getAllStudents();
    Student getStudentById(Long id);
    Student updateStudent(Long id, Student studentDetails);
    Student partialUpdate(Long id, Student updates);
    void deleteStudent(Long id);
    boolean exists(Long id);
}