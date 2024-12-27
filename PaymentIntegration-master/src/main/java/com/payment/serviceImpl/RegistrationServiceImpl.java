package com.payment.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.entity.RegistrationModel;
import com.payment.repo.RegistrationRepo;
import com.payment.service.RegistrationService;
@Service
public class RegistrationServiceImpl implements RegistrationService{
	
	@Autowired
	private RegistrationRepo repo;

	@Override
	public RegistrationModel save(RegistrationModel model) {
			return repo.save(model);
	}

}
