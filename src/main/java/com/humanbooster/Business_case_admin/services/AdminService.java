package com.humanbooster.Business_case_admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humanbooster.Business_case_admin.model.Admin;
import com.humanbooster.Business_case_admin.repository.AdminRepository;


@Service
public class AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	public List<Admin> getAdmins(){return this.adminRepository.findAll();}
	
	public void saveOrUpdateAdmin(Admin admin) {this.adminRepository.save(admin);}
			
	public void deleteAdmin(Admin admin) {this.adminRepository.delete(admin);}
}
