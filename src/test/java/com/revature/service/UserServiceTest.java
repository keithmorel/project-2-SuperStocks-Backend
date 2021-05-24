package com.revature.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.revature.dao.UserDAO;
import com.revature.exception.BadParameterException;
import com.revature.exception.RegistrationException;
import com.revature.exception.UserNotFoundException;
import com.revature.model.User;
import com.revature.model.UserRole;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

	@Mock
	private UserDAO userDAO;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	void setup() {

		when(userDAO.getRoleById(1))
			.thenReturn(new UserRole(1, "User"));
		
		when(userDAO.getRoleById(2))
			.thenReturn(new UserRole(2, "Admin"));

		UserRole role = new UserRole(1, "User");
		User user = new User(1, "username", "password", "test@email.com", "first", "last", role, new HashSet<>());
		when(userDAO.getUserByUsernameAndPassword("username", "password"))
			.thenReturn(user);

		when(userDAO.registerNewUser("username", "password", "test@email.com", "first", "last", 1))
			.thenReturn(user);
		
		UserRole adminRole = new UserRole(2, "Admin");
		User adminUser = new User(2, "admin", "password", "admin@email.com", "admin", "user", adminRole, new HashSet<>());
		when(userDAO.registerNewUser("admin", "password", "admin@email.com", "admin", "user", 2))
			.thenReturn(adminUser);

		User updateUser = new User(1, "newusername", "newpassword", "new@email.com", "newfirst", "newlast", role, new HashSet<>());
		when(userDAO.updateUser(1, "newusername", "newpassword", "new@email.com", "newfirst", "newlast"))
			.thenReturn(updateUser);

	}

	@Test
	void test_login_success() throws BadParameterException, UserNotFoundException {

		User actual = userService.login("username", "password");

		UserRole userRole = userDAO.getRoleById(1);
		User expected = new User(1, "username", "password", "test@email.com", "first", "last", userRole,
				new HashSet<>());

		assertEquals(expected, actual);

	}

	@Test
	void test_login_throwsBadParameterException() throws UserNotFoundException {

		try {
			userService.login("", "");
			fail("BadParameterException was not thrown");
		} catch (BadParameterException e) {
			assertEquals("Username and password can't be blank", e.getMessage());
		}

	}

	@Test
	void test_register_success() throws BadParameterException, RegistrationException {

		User actual = userService.register("username", "password", "test@email.com", "first", "last", "User");

		UserRole userRole = userDAO.getRoleById(1);
		User expected = new User(1, "username", "password", "test@email.com", "first", "last", userRole,
				new HashSet<>());

		assertEquals(expected, actual);

	}
	
	@Test
	void test_register_setAdminRole() throws BadParameterException, RegistrationException {

		User actual = userService.register("admin", "password", "admin@email.com", "admin", "user", "Admin");

		UserRole adminRole = userDAO.getRoleById(2);
		User expected = new User(2, "admin", "password", "admin@email.com", "admin", "user", adminRole, new HashSet<>());

		assertEquals(expected, actual);

	}

	@Test
	void test_register_fail_blankParams() throws BadParameterException, RegistrationException {

		try {
			userService.register("", "", "", "", "", "");
			fail("BadParameterException not thrown");
		} catch (BadParameterException e) {
			assertEquals("All info must be provided and not blank", e.getMessage());
		}

	}

	@Test
	void test_register_fail_invalidEmail() throws BadParameterException, RegistrationException {

		try {
			userService.register("username", "password", "test", "first", "last", "User");
			fail("BadParameterException not thrown for invalid email");
		} catch (BadParameterException e) {
			assertEquals("Email must be of the form '[email]@[website].[domain]'", e.getMessage());
		}

	}

	@Test
	void test_register_fail_invalidRole() throws BadParameterException, RegistrationException {

		try {
			userService.register("username", "password", "test@email.com", "first", "last", "Test");
			fail("BadParameterException not thrown for invalid role");
		} catch (BadParameterException e) {
			assertEquals("Role must be either User or Admin", e.getMessage());
		}

	}

//	@Test
//	void test_updateUserInfo_success() throws BadParameterException {
//
//		User actual = userService.updateUserInfo(1, "newusername", "newpassword", "new@email.com", "newfirst", "newlast");
//
//		UserRole userRole = userDAO.getRoleById(1);
//		User expected = new User(1, "newusername", "newpassword", "new@email.com", "newfirst", "newlast", userRole,
//				new HashSet<>());
//
//		assertEquals(expected, actual);
//
//	}

	@Test
	void test_updateUserInfo_fail_blankParams() throws BadParameterException {

		try {
			userService.updateUserInfo(1, "", "", "", "", "");
			fail("BadParameterException not thrown");
		} catch (BadParameterException e) {
			assertEquals("All info must be provided and not blank", e.getMessage());
		}

	}
	
	@Test
	void test_updateUserInfo_fail_invalidEmail() throws BadParameterException {

		try {
			userService.updateUserInfo(1, "newusername", "newpassword", "test", "newfirst", "newlast");
			fail("BadParameterException not thrown for invalid email");
		} catch (BadParameterException e) {
			assertEquals("Email must be of the form '[email]@[website].[domain]'", e.getMessage());
		}

	}

}
