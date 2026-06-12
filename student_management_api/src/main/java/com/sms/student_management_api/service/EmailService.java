package com.sms.student_management_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendEnrollmentEmail(String to, String studentName, String courseName) {
        String subject = "Course Enrollment Confirmation";
        String body = "Dear " + studentName + ",\n\n"
                + "You have been successfully enrolled in " + courseName + ".\n\n"
                + "Welcome aboard!\n\n"
                + "Best regards,\n"
                + "Student Management System";
        sendEmail(to, subject, body);
    }

    public void sendGradeEmail(String to, String studentName, String courseName,
                               Double score, String grade) {
        String subject = "Grade Released — " + courseName;
        String body = "Dear " + studentName + ",\n\n"
                + "Your grade for " + courseName + " has been released.\n\n"
                + "Score: " + score + "\n"
                + "Grade: " + grade + "\n\n"
                + "Best regards,\n"
                + "Student Management System";
        sendEmail(to, subject, body);
    }
}
