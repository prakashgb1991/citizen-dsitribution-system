package com.cds.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.cds.model.User;

public class CSVHelper {
	public static String TYPE = "text/csv";
	static String[] HEADERs = { "Id", "Title", "Description", "Published" };

	/**
	 * checks if it is a valid csv file
	 * @param file
	 * @return
	 */
	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	/**
	 * This method helps to convert the input stream to the list of users
	 * @param is
	 * @return
	 */
	public static List<User> csvToUsers(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<User> userList = new ArrayList<User>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				User usr = new User(csvRecord.get("User"), new BigDecimal(csvRecord.get("Salary")), new Date(), 'A');
				userList.add(usr);
			}

			return userList;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

}