package com.sms.student_management_api.repository;

import com.sms.student_management_api.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);

    // New: Custom query that handles both searching and pagination simultaneously
    Page<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrCourseContainingIgnoreCase(
            String firstName, String lastName, String course, Pageable pageable);
}