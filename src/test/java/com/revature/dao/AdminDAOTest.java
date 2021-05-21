package com.revature.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.revature.model.User;
import com.revature.model.UserRole;

@ExtendWith(SpringExtension.class)
@ContextHierarchy({ @ContextConfiguration("classpath:applicationContext.xml"),
		@ContextConfiguration("classpath:dispatcherContext.xml") })
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
class AdminDAOTest {

	@Autowired
	private AdminDAO adminDAO;

	@Autowired
	private UserDAO userDAO;


	@Test
	@Transactional
	@Commit
	@Order(0)
	void test_Success_getUser() {

		UserRole userRole = userDAO.addUserRole(0, "Admin");
		userDAO.registerNewUser("user", "password", "test@admin.com", "user", "lastname", 1);
		
		User actual = adminDAO.getUser(1);
		User expected = new User(1, "user", "5f4dcc3b5aa765d61d8327deb882cf99", "test@admin.com", "user", "lastname", userRole, new HashSet<>());
		
		assertEquals(expected, actual);
	}
	
	@Test
	@Transactional
	@Commit
	@Order(1)
	void test_Success_getAllUser() {

		UserRole userRole = userDAO.getRoleById(1);		
		userDAO.registerNewUser("userName", "password", "test@test.com", "user", "lastname", 1);
		
		List<User> actual = adminDAO.getUsers();
		List<User> expected = new ArrayList<User>();
		expected.add(new User(1, "user", "5f4dcc3b5aa765d61d8327deb882cf99", "test@admin.com", "user", "lastname", userRole, new HashSet<>()));
		expected.add(new User(2, "userName", "5f4dcc3b5aa765d61d8327deb882cf99", "test@test.com", "user", "lastname", userRole, new HashSet<>()));
		
		assertEquals(expected, actual);
	}

}
