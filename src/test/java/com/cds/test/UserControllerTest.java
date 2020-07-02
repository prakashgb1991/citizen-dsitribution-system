package com.cds.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.cds.model.User;
import com.cds.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getUsers() throws Exception {

		ResponseEntity<String> response = restTemplate
				.getForEntity(new URL("http://localhost:" + port + "/users/").toString(), String.class);
		assertEquals(200, response.getStatusCodeValue());

	}
	
	@Test
	public void getUsersEmpty() throws Exception {
		ResponseEntity<String> response = restTemplate
				.getForEntity(new URL("http://localhost:" + port + "/users/?min=-1&max=-3").toString(), String.class);
		assertEquals(204, response.getStatusCodeValue());

	}
	
	@Test
	public void getUsersBadReq() throws Exception {
		ResponseEntity<String> response = restTemplate
				.getForEntity(new URL("http://localhost:" + port + "/users/?min=test&max=-3").toString(), String.class);
		assertEquals(400, response.getStatusCodeValue());

	}

	@Test
	public void uploadTest() throws Exception {
		String text = "Text, to, be ,uploaded.";
		MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", text.getBytes());
		mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(file).characterEncoding("UTF-8"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void uploadTestFail() throws Exception {
		String text = "Text, to, be ,uploaded.";
		MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/txt", text.getBytes());
		mockMvc.perform(MockMvcRequestBuilders.multipart("/users/upload").file(file).characterEncoding("UTF-8"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

}