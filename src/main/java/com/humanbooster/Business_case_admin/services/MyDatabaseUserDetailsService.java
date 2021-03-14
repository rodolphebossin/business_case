package com.humanbooster.Business_case_admin.services;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.humanbooster.Business_case_admin.BusinessCaseAdminApplication;
import com.humanbooster.Business_case_admin.model.User;
import com.humanbooster.Business_case_admin.repository.UserRepository;
import org.slf4j.LoggerFactory;





public class MyDatabaseUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;


	   private static final org.slf4j.Logger log = LoggerFactory.getLogger(BusinessCaseAdminApplication.class);
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(getUserAuthority(user)));
	
		log.info("_____ ROLE AJOUTE !");
		log.info(user.getClass().getAnnotation(DiscriminatorValue.class).value());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				grantedAuthorities); // (2)
	}
	
	public String getUserAuthority(User user) {
		return user.getClass().getAnnotation(DiscriminatorValue.class).value();
	}
}
