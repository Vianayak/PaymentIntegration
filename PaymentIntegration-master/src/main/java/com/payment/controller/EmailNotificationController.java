package com.payment.controller;




import com.payment.entity.EmailRequest;
import com.payment.entity.RegistrationModel;
import com.payment.entity.VerifyOtpRequest;
import com.payment.service.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class EmailNotificationController {

    @Autowired
    private EmailNotificationService emailNotificationService;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegistrationModel user) {
        try {
            emailNotificationService.saveUser(user);
            return "User registered successfully! OTP sent to email: " + user.getEmail();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest) {
        boolean isVerified = emailNotificationService.verifyOtp(verifyOtpRequest.getEmail(), verifyOtpRequest.getOtp());
        if (isVerified) {
            return "OTP verified successfully!";
        } else {
            return "Invalid OTP. Please try again.";
        }
    }

    @PostMapping("/send-otp")
    public String sendOtpToRegisteredEmail(@RequestBody EmailRequest emailRequest) {
        boolean isOtpSent = emailNotificationService.sendOtpToRegisteredEmail(emailRequest.getEmail());
        if (isOtpSent) {
            return "OTP has been sent to your email: " + emailRequest.getEmail();
        } else {
            return "Email not found. Please register first.";
        }
    }
}

