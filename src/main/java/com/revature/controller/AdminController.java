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

import com.revature.annotation.AdminOnly;
import com.revature.exception.UserNotFoundException;
import com.revature.model.User;
import com.revature.service.AdminService;

@Controller
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;
	
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

	@GetMapping(path = "user")
	@AdminOnly
	public ResponseEntity<Object> getAllUser() {
		
		String requestString = String.format(requestStrFormat, request.getMethod(), request.getRequestURI());
		logger.info(requestString);

		List<User> user = adminService.getAllUser();

		return ResponseEntity.status(200).body(user);
	}

}
