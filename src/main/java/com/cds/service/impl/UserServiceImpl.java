package com.cds.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cds.model.User;
import com.cds.repository.UserRepository;
import com.cds.service.UserService;
import com.cds.util.CSVHelper;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository repository;

	@Override
	public void save(MultipartFile file) {
		try {
			List<User> users = CSVHelper.csvToUsers(file.getInputStream());
			repository.saveAll(users);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	@Override
	public void saveFile(File file) {
		try {
			List<User> users = CSVHelper.csvToUsers(new FileInputStream(file.getAbsolutePath()));
			repository.saveAll(users);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	@Override
	public List<User> getUsers(BigDecimal min, BigDecimal max) throws Exception {
		return repository.findUsers(min, max);
	}
}