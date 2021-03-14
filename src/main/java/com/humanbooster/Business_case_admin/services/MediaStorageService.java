package com.humanbooster.Business_case_admin.services;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

public interface MediaStorageService {
	
	  public void init();

	  public void save(MultipartFile file, String newFileName);

	  public Resource load(String filename);

}
