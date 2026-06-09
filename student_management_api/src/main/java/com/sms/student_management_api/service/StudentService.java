package com.sms.student_management_api.service;

import com.sms.student_management_api.dto.StudentDTO;
import java.util.List;

public interface StudentService {
    StudentDTO createStudent(StudentDTO studentDTO);
    List<StudentDTO> getAllStudents();
    StudentDTO getStudentById(Long id);
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);
    void deleteStudent(Long id);
}
