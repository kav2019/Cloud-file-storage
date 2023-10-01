package ru.kovshov.cloud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import ru.kovshov.cloud.dto.UserDTO;
import ru.kovshov.cloud.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CloudFileStorageApplicationTests {

	@Autowired
	private ObjectMapper objectMapper; // converts object to json

	@LocalServerPort
	private int port;

	@Autowired
	private TestH2Repository h2Repository;

	private String baseUrl = "http://localhost:";


	public static RestTemplate restTemplate;

	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	public static void init(){
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp(){
		StringBuilder sb = new StringBuilder();
		sb.append(baseUrl);
		sb.append(this.port);
		sb.append("/api/user");
	}

	@Test
	@WithMockUser
	public void addUser() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(baseUrl);
		sb.append(this.port);
		sb.append("/api/user/create");

		User user = new User();
		user.setEmail("controller@test.ru");
		user.setUsername("ControllerTest");
		user.setPassword("test123");


		String port = sb.toString();


//		UserDTO userResponce = restTemplate.postForObject(port, objectMapper.writeValueAsString(user), UserDTO.class);
//		assertEquals(userResponce.getUsername(), user.getUsername());
//		assertEquals(userResponce.getEmail(), user.getEmail());


		mockMvc.perform(post(port)

						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user))).andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("ControllerTest"))
				.andExpect(jsonPath("$.email").value("controller@test.ru"));
		assertEquals(1, h2Repository.findAll().size());


	}

}
