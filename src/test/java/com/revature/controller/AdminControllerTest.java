package com.revature.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exception.UserNotFoundException;
import com.revature.model.User;
import com.revature.model.UserRole;
import com.revature.service.AdminService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AdminControllerTest {

	MockMvc mockMvc;

	@Mock
	HttpServletRequest request;

	@Mock
	AdminService adminService;

	@InjectMocks
	AdminController ac;

	@BeforeEach
	void setup() throws UserNotFoundException {

		UserRole userRole = new UserRole(1, "User");
		User user = new User(1, "username", "password", "test@email.com", "first", "last", userRole, new HashSet<>());

		when(adminService.getUserById(1)).thenReturn(user);

		List<User> users = new ArrayList<User>();
		users.add(new User(1, "user", "5f4dcc3b5aa765d61d8327deb882cf99", "test@admin.com", "user", "lastname",
				userRole, new HashSet<>()));
		when(adminService.getAllUser()).thenReturn(users);

		this.mockMvc = MockMvcBuilders.standaloneSetup(ac).build();
	}

	@Test
	void test_getUserById_success() throws Exception {

		UserRole userRole = new UserRole(1, "User");
		User expected = new User(1, "username", "password", "test@email.com", "first", "last", userRole, new HashSet<>());

		
		ObjectMapper om = new ObjectMapper();
		String expectedJson = om.writeValueAsString(expected);

		this.mockMvc.perform(get("/user/1"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().json(expectedJson));
	}

	@Test
	void test_getUsers_success() throws Exception {

		UserRole role = new UserRole(1, "User");
		List<User> expected = new ArrayList<User>();
		expected.add(new User(1, "user", "5f4dcc3b5aa765d61d8327deb882cf99", "test@admin.com", "user", "lastname", role, new HashSet<>()));
				
		ObjectMapper om = new ObjectMapper();
		String expectedJson = om.writeValueAsString(expected);

		this.mockMvc.perform(get("/user"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().json(expectedJson));
	}
}
