package com.payment.serviceImpl;


import com.payment.entity.RegistrationModel;
import com.payment.repo.EmailNotificationRepo;
import com.payment.service.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {

    @Autowired
    private EmailNotificationRepo repository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public RegistrationModel saveUser(RegistrationModel user) {
        // Check if email already exists
        Optional<RegistrationModel> existingUser = repository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        // Generate OTP
        String otp = generateOtp();
        user.setOtp(otp);

        // Send OTP to email
        sendOtpToEmail(user.getEmail(), otp);

        // Save user in the database
        return repository.save(user);
    }

    @Override
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public void sendOtpToEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp + ". Use this code to complete your registration.");

        mailSender.send(message);
    }
    @Override
    public boolean verifyOtp(String email, String otp) {
        Optional<RegistrationModel> user = repository.findByEmail(email);
        if (user.isPresent() && user.get().getOtp().equals(otp)) {
            return true; // OTP is correct
        }
        return false; // OTP is incorrect
    }


@Override
public boolean sendOtpToRegisteredEmail(String email) {
    // Check if the email exists in the database
    Optional<RegistrationModel> user = repository.findByEmail(email);
    if (user.isPresent()) {
        String otp = generateOtp(); // Generate a new OTP
        RegistrationModel existingUser = user.get();
        existingUser.setOtp(otp);   // Update the OTP in the database
        repository.save(existingUser);

        sendOtpToEmail(email, otp); // Send the OTP to the email
        return true;
    }
    return false; // Email not found
}
}

