package com.sms.student_management_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsConfig {

    private final PasswordEncoder passwordEncoder;

    public UserDetailsConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("Kent")
                .password(passwordEncoder.encode("Kent_jr"))
                .roles("ADMIN")
                .build();

        UserDetails teacher = User.builder()
                .username("teacher1")
                .password(passwordEncoder.encode("Teacher_123"))
                .roles("TEACHER")
                .build();

        UserDetails student = User.builder()
                .username("student1")
                .password(passwordEncoder.encode("Student_123"))
                .roles("STUDENT")
                .build();

        return new InMemoryUserDetailsManager(admin, teacher, student);
    }
}