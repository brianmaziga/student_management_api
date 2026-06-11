package com.sms.student_management_api.service;

import com.sms.student_management_api.dto.StudentDTO;
import org.springframework.data.domain.Page;

public interface StudentService {
    StudentDTO saveStudent(StudentDTO studentDTO);
    StudentDTO getStudentById(Long id);
    StudentDTO partialUpdate(Long id, StudentDTO updates);
    void deleteStudent(Long id);

    // Updated: Replaces the old getAllStudents() method to support filters and pages
    Page<StudentDTO> getStudents(String keyword, int page, int size);
}