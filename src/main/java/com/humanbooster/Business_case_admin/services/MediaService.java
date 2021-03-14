package com.humanbooster.Business_case_admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.humanbooster.Business_case_admin.model.Media;
import com.humanbooster.Business_case_admin.repository.MediaRepository;

@Service
public class MediaService {
	
	@Autowired
	private MediaRepository mediaRepository;
	
	public List<Media> getMedias(){return this.mediaRepository.findAll();}
	
	public void saveOrUpdateMedia(Media media) {this.mediaRepository.save(media);}
			
	public void deleteMedia(Media media) {this.mediaRepository.delete(media);}
	
	

}
