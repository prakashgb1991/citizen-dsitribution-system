package com.cds.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cds.model.User;
import com.cds.service.UserService;
import com.cds.util.CSVHelper;
import com.cds.util.ResponseMessage;

@RestController
@RequestMapping("/users")
public class UserController {

	private static Logger log = Logger.getLogger(UserController.class);
	
	@Autowired
	UserService userService;

	@PostConstruct
	protected void init() {
		log.info("Uploading the file: User.csv");
		try
		{
		File file = new File("./src/main/resources/user.csv");
		userService.saveFile(file);
		}
		catch(Exception e)
		{
			log.error("cannot uploadFile");
		}
	}

	/**
	 * This method is used to upload the csv file to the database
	 * @param file
	 * @return
	 */
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		log.info("Uploading the file");
		if (CSVHelper.hasCSVFormat(file)) {
			try {
				userService.save(file);

				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				log.error("cannot uploadFile",e);
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	/**
	 * This method is used to fetch the users on salary range
	 * @param min
	 * @param max
	 * @return
	 */
	@GetMapping("/")
	public ResponseEntity<List<User>> getUsers(
			@RequestParam(value = "min", required = false, defaultValue = "0") BigDecimal min,
			@RequestParam(value = "max", required = false, defaultValue = "4000") BigDecimal max) {
		log.info("fetching the Users");
		try {
			List<User> users = userService.getUsers(min, max);

			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			log.error("cannot fetch users",e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
