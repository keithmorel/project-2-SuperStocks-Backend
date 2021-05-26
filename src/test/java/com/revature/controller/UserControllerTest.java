package com.revature.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exception.BadParameterException;
import com.revature.exception.RegistrationException;
import com.revature.exception.UserNotFoundException;
import com.revature.model.User;
import com.revature.model.UserRole;
import com.revature.service.UserService;
import com.revature.template.LoginTemplate;
import com.revature.template.MessageTemplate;
import com.revature.template.RegisterTemplate;
import com.revature.template.UpdateUserTemplate;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserControllerTest {
	
	MockMvc mockMvc;
	
	@Mock
	HttpServletRequest request;
	
	@Mock
	UserService userService;
	
	@InjectMocks
	UserController uc;
	
	@BeforeEach
	void setup() throws BadParameterException, UserNotFoundException, RegistrationException {
		
		HttpSession session = new MockHttpSession();
		
		when(request.getSession(true))
			.thenReturn(session);
		
		UserRole userRole = new UserRole(1, "User");
		User user = new User(1, "username", "password", "test@email.com", "first", "last", userRole, new HashSet<>());
		when(userService.login("username", "password"))
			.thenReturn(user);
		
		when(userService.register("username", "password", "test@email.com", "first", "last", "User"))
			.thenReturn(user);
		
		User updatedUser = new User(1, "new", "password", "new@email.com", "new", "name", userRole, new HashSet<>());
		when(userService.updateUserInfo(1, "new", "password", "test@email.com", "new", "name"))
			.thenReturn(updatedUser);
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(uc).build();
		
	}

	@Test
	void test_login_success() throws Exception {
		
		LoginTemplate loginTemplate = new LoginTemplate("username", "password");
		ObjectMapper om = new ObjectMapper();
		String expectedJSON = om.writeValueAsString(loginTemplate);
		
		MessageTemplate messageTemplate = new MessageTemplate("Successfully logged in");
		String expectedOutput = om.writeValueAsString(messageTemplate);
		
		this.mockMvc.perform(post("/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(expectedJSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(expectedOutput));
		
	}
	
	@Test
	void test_register_success() throws Exception {
		
		RegisterTemplate registerTemplate = new RegisterTemplate("username", "password", "test@email.com", "first", "last", "User");
		ObjectMapper om = new ObjectMapper();
		String expectedJSON = om.writeValueAsString(registerTemplate);
		
		MessageTemplate messageTemplate = new MessageTemplate("Successfully registered user");
		String expectedOutput = om.writeValueAsString(messageTemplate);
		
		this.mockMvc.perform(post("/register")
			.contentType(MediaType.APPLICATION_JSON)
			.content(expectedJSON))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().json(expectedOutput));
		
	}
	
	@Test
	void test_logout_success() throws Exception {
		
		ObjectMapper om = new ObjectMapper();
		MessageTemplate messageTemplate = new MessageTemplate("Successfully logged out user");
		String expectedOutput = om.writeValueAsString(messageTemplate);
		
		this.mockMvc.perform(post("/logout")
			.contentType(MediaType.APPLICATION_JSON)
			.content(expectedOutput))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(expectedOutput));
		
	}
	
	@Test
	void test_updateUserInfo_success() throws Exception {
		
		UpdateUserTemplate updateTemplate = new UpdateUserTemplate("new", "password", "test@email.com", "new", "name");
		ObjectMapper om = new ObjectMapper();
		String expectedJSON = om.writeValueAsString(updateTemplate);
		
		MessageTemplate messageTemplate = new MessageTemplate("Successfully updated user information");
		String expectedOutput = om.writeValueAsString(messageTemplate);
		
		this.mockMvc.perform(put("/user/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(expectedJSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(expectedOutput));
		
	}

}
