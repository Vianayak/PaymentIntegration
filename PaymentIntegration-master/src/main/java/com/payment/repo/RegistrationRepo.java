package com.payment.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.entity.RegistrationModel;


public interface RegistrationRepo extends JpaRepository<RegistrationModel, Integer> {
}
