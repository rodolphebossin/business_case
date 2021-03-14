package com.humanbooster.Business_case_admin.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MediaStorageServiceImpl implements MediaStorageService{
	
	  private final Path root = Paths.get("src/main/resources/static/uploads");

	  @Override
		public void init() {
			if (!Files.exists(root)) {
				try {
					Files.createDirectory(root);
				} catch (IOException e) {
					throw new RuntimeException("Impossible de créer le dossier des uploads!");
				}
			}
		}

	  @Override
	  public void save(MultipartFile file, String newFileName) {
	    try {
	    	
	      Files.copy(file.getInputStream(), this.root.resolve(newFileName), StandardCopyOption.REPLACE_EXISTING);
	    } catch (Exception e) {
	      throw new RuntimeException("Impossible d'uploader le fichier. Erreur: " + e.getMessage());
	    }
	  }

	  @Override
	  public Resource load(String filename) {
	    try {
	      Path file = root.resolve(filename);
	      Resource resource = new UrlResource(file.toUri());

	      if (resource.exists() || resource.isReadable()) {
	        return resource;
	      } else {
	        throw new RuntimeException("Impossible de lire le fichier!");
	      }
	    } catch (MalformedURLException e) {
	      throw new RuntimeException("Erreur: " + e.getMessage());
	    }
	  }
	  
		public String sortMediabasedOnType(MultipartFile file) {
			try {
				String contentType = file.getContentType();
				String mediaType = "";
				if (contentType.equals("image/png") || contentType.equals("image/jpg")
						|| contentType.equals("image/jpeg")) {
					mediaType = "image";
				} else if (contentType.equals("video/mpeg") || contentType.equals("video/mp4")
						|| contentType.equals("video/quicktime")) {
					mediaType = "video";
				}
				return mediaType;

			} catch (InvalidMimeTypeException e) {
				throw new RuntimeException("Erreur: " + e.getMessage());
			}
		}
	  
	  public String renameUploadedFile(MultipartFile file) {
		  String fileName = file.getOriginalFilename();
		  int lastIndex = fileName.lastIndexOf('.');
		  String fileTypeExtension = fileName.substring(lastIndex, fileName.length());
		  String uniqueID = UUID.randomUUID().toString();
		  String newFileName = uniqueID + fileTypeExtension;
		  return newFileName;
	  }
	  

}
