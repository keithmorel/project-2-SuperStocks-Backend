package com.revature.controller;

import java.util.List;

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

	@Autowired
	private AdminService adminService;

	@GetMapping(path = "user/{id}")
	@AdminOnly
	public ResponseEntity<Object> getUserById(@PathVariable("id") int id) throws UserNotFoundException {

		User user = adminService.getUserById(id);

		return ResponseEntity.status(200).body(user);
	}

	@GetMapping(path = "user")
	@AdminOnly
	public ResponseEntity<Object> getAllUser() {

		List<User> user = adminService.getAllUser();

		return ResponseEntity.status(200).body(user);
	}

}
