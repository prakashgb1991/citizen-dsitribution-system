package com.cds.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cds.model.User;

public interface UserService {

	/**
	 * This method is used to save the file 
	 * @param file
	 */
	public void save(MultipartFile file);
	
	/**
	 * This method is used to save the file on application initialization
	 * @param file
	 */
	public void saveFile(File file);
	
	/**
	 * This method returns the list of users
	 * @param min
	 * @param max
	 * @return
	 * @throws Exception 
	 */
	public List<User> getUsers(BigDecimal min, BigDecimal max) throws Exception;

}
