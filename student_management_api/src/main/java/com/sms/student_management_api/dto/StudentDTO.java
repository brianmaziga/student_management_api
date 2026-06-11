package com.sms.student_management_api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StudentDTO {
    private Long id;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    private String course;

    @Pattern(regexp = "^07[0-9]{8}$", message = "Phone number must be 10 digits starting with 07")
    private String phoneNumber;
}