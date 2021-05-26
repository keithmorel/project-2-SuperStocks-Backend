package com.revature.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.revature.annotation.AdminOnly;
import com.revature.exception.BadParameterException;
import com.revature.exception.UserNotFoundException;
import com.revature.model.User;
import com.revature.service.AdminService;
import com.revature.service.UserService;
import com.revature.template.MessageTemplate;
import com.revature.template.UpdateUserTemplate;

import jakarta.validation.Valid;

@Controller
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpServletRequest request;
	
	private String requestStrFormat = "%s request made to: %s";

	@GetMapping(path = "user/{id}")
	@AdminOnly
	public ResponseEntity<Object> getUserById(@PathVariable("id") int id) throws UserNotFoundException {

		String requestString = String.format(requestStrFormat, request.getMethod(), request.getRequestURI());
		logger.info(requestString);
		
		User user = adminService.getUserById(id);

		return ResponseEntity.status(200).body(user);
	}
	
	@PutMapping(path="admin/user/{id}")
	@AdminOnly
	public ResponseEntity<Object> updateUserById(@PathVariable("id") int id, @RequestBody @Valid UpdateUserTemplate updateUserTemplate) throws BadParameterException {
		
		String requestString = String.format(requestStrFormat, request.getMethod(), request.getRequestURI());
		logger.info(requestString);
		
		userService.updateUserInfo(id, updateUserTemplate.getUsername(), updateUserTemplate.getPassword(),
				updateUserTemplate.getEmail(), updateUserTemplate.getFirstName(), updateUserTemplate.getLastName());

		return ResponseEntity.status(200).body(new MessageTemplate("Successfully updated user information"));
		
	}

	@GetMapping(path = "user")
	@AdminOnly
	public ResponseEntity<Object> getAllUser() {
		
		String requestString = String.format(requestStrFormat, request.getMethod(), request.getRequestURI());
		logger.info(requestString);

		List<User> user = adminService.getAllUser();

		return ResponseEntity.status(200).body(user);
	}

}
