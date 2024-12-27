package com.payment.service;

import com.payment.entity.RegistrationModel;


public interface EmailNotificationService {
    RegistrationModel saveUser(RegistrationModel user);
    String generateOtp();
    void sendOtpToEmail(String email, String otp);
    boolean verifyOtp(String email, String otp);
    boolean sendOtpToRegisteredEmail(String email);
}

