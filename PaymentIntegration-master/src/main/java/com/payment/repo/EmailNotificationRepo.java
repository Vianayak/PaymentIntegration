package com.payment.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.entity.RegistrationModel;




public interface EmailNotificationRepo extends JpaRepository<RegistrationModel, Integer> {
    Optional<RegistrationModel> findByEmail(String email); // To check if email already exists
}
