package com.humanbooster.Business_case_admin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humanbooster.Business_case_admin.model.User;
import com.humanbooster.Business_case_admin.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User getUserByUsername(String username) {return this.userRepository.findByUsername(username);}

}
