package com.sms.student_management_api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StudentDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String course;
    private LocalDateTime createdAt;
}
