package com.revature.integration;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.UserDAO;
import com.revature.model.User;
import com.revature.model.UserRole;
import com.revature.template.LoginTemplate;
import com.revature.template.MessageTemplate;
import com.revature.template.RegisterTemplate;
import com.revature.template.UpdateUserTemplate;

@ExtendWith(SpringExtension.class)
@ContextHierarchy({
	@ContextConfiguration("classpath:applicationContext.xml"),
	@ContextConfiguration("classpath:dispatcherContext.xml")
})
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode=ClassMode.BEFORE_CLASS)
class UserTest {
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	UserDAO userDAO;
	
	
	private MockHttpSession session = new MockHttpSession();
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setup() {		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		this.objectMapper = new ObjectMapper();
	}

	@Test
	@Transactional
	@Commit
	@Order(0)
	void test_registerUser_success() throws Exception {
		
		RegisterTemplate rt = new RegisterTemplate("username", "password", "email@gmail.com", "First", "Last", "User");
		String jsonInput = objectMapper.writeValueAsString(rt);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInput);
		
		MessageTemplate expected = new MessageTemplate("Successfully registered user");
		String expectedJson = objectMapper.writeValueAsString(expected);
		
		this.mockMvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().json(expectedJson));
		
	}
	
	@Test
	@Order(1)
	void test_registerUser_fail_duplicateUser() throws Exception {
		
		RegisterTemplate rt = new RegisterTemplate("username", "password", "email@gmail.com", "First", "Last", "User");
		String jsonInput = objectMapper.writeValueAsString(rt);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInput);
		
		MessageTemplate expected = new MessageTemplate("There is already an account with that username and email. Change one and try again.");
		String expectedJson = objectMapper.writeValueAsString(expected);
		
		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().json(expectedJson));
		
	}
	
	@Test
	@Order(1)
	void test_loginUser_success() throws Exception {
		
		LoginTemplate rt = new LoginTemplate("username", "password");
		String jsonInput = objectMapper.writeValueAsString(rt);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInput);
		
		MessageTemplate expected = new MessageTemplate("Successfully logged in");
		String expectedJson = objectMapper.writeValueAsString(expected);
		
		this.mockMvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(expectedJson));
		
	}
	
	@Test
	@Order(1)
	void test_loginUser_fail_invalidUsernameAndPassword() throws Exception {
		
		LoginTemplate rt = new LoginTemplate("NotARealUsername", "NotARealPassword");
		String jsonInput = objectMapper.writeValueAsString(rt);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonInput);
		
		MessageTemplate expected = new MessageTemplate("Username or Password provided are incorrect");
		String expectedJson = objectMapper.writeValueAsString(expected);
		
		this.mockMvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.content().json(expectedJson));
		
	}
	
	@Test
	@Order(2)
	void test_currentUser_success() throws Exception {
		MockHttpSession session = this.session;
		UserRole userRole = new UserRole(1, "User");
		User user = new User(1, "username", "5f4dcc3b5aa765d61d8327deb882cf99", "user@gmail.com", "first", "last", userRole, new HashSet<>());
		session.setAttribute("loggedInUser", user);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/current")
				.session(session);
		
		User expected = new User(1, "username", "5f4dcc3b5aa765d61d8327deb882cf99", "user@gmail.com", "first", "last", userRole, new HashSet<>());
		String expectedJson = objectMapper.writeValueAsString(expected);
		
		this.mockMvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(expectedJson));
		
	}
	
	@Test
	@Order(2)
	void test_currentUser_fail_notLoggedIn() throws Exception {

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/current")
				.session(session);
		
		MessageTemplate expected = new MessageTemplate("User is not logged in");
		String expectedJson = objectMapper.writeValueAsString(expected);
		
		this.mockMvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().json(expectedJson));
		
	}
	
//	@Test
//	@Order(2)
//	void test_updateUserInfo_success() throws Exception {
//		MockHttpSession session = this.session;
//		UserRole userRole = new UserRole(1, "User");
//		User user = new User(1, "username", "5f4dcc3b5aa765d61d8327deb882cf99", "user@gmail.com", "first", "last", userRole, new HashSet<>());
//		session.setAttribute("loggedInUser", user);
//		
//		UpdateUserTemplate ut = new UpdateUserTemplate("username", "password", "user@gmail.com", "newFirst", "newLast");
//		String jsonInput = objectMapper.writeValueAsString(ut);
//
//		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//				.put("/user/1")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(jsonInput)
//				.session(session);
//		
//		MessageTemplate expected = new MessageTemplate("Successfully updated user information");
//		String expectedJson = objectMapper.writeValueAsString(expected);
//		
//		this.mockMvc.perform(builder)
//			.andExpect(MockMvcResultMatchers.status().isOk())
//			.andExpect(MockMvcResultMatchers.content().json(expectedJson));
//		
//	}

}
