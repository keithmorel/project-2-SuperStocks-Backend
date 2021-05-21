package com.revature.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.revature.dao.AdminDAO;
import com.revature.dao.UserDAO;
import com.revature.exception.UserNotFoundException;
import com.revature.model.User;
import com.revature.model.UserRole;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AdminServiceTest {
	
	@Mock
	private AdminDAO adminDAO;
	
	@Mock
	private UserDAO userDAO;
	
	@InjectMocks
	private AdminService adminService;
	
	@BeforeEach
	void setup() {
		
		when(userDAO.getRoleById(1))
		.thenReturn(new UserRole(1, "User"));
		
		UserRole role = new UserRole(1, "User");
		User user = new User(1, "username", "password", "test@email.com", "first", "last", role, new HashSet<>());
		
		when(adminDAO.getUser(1)).thenReturn(user);
		
		List<User> users = new ArrayList<User>();
		users.add(new User(1, "user", "5f4dcc3b5aa765d61d8327deb882cf99", "test@admin.com", "user", "lastname", role, new HashSet<>()));
		
		when(adminDAO.getUsers()).thenReturn(users);			
	}
	
	@Test
	void test_getUserByID_success() throws UserNotFoundException {
		User actual = adminService.getUserById(1);
		
		UserRole role = new UserRole(1, "User");
		User expected = new User(1, "username", "password", "test@email.com", "first", "last", role,
				new HashSet<>());
		
		assertEquals(expected, actual);
	}

	@Test
	void test_getUsers_success() {
		List<User> actual =  adminService.getAllUser();
		
		UserRole role = new UserRole(1, "User");
		List<User> expected = new ArrayList<User>();
		expected.add(new User(1, "user", "5f4dcc3b5aa765d61d8327deb882cf99", "test@admin.com", "user", "lastname", role, new HashSet<>()));
		
		assertEquals(expected, actual);
	}
}
