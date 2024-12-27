package com.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.entity.RegistrationModel;
import com.payment.service.RegistrationService;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {
	@Autowired
	private RegistrationService service;

	@PostMapping("/newSignUp")
	public RegistrationModel save(@RequestBody RegistrationModel newOne) {
			return service.save(newOne);
	}
}
