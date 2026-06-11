package com.sms.student_management_api.repository;

import com.sms.student_management_api.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // Add this line exactly:
    List<Student> findByCourse(String course);
}