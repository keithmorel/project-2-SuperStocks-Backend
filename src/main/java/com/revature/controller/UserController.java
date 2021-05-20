package com.revature.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.revature.annotation.LoggedInOnly;
import com.revature.exception.BadParameterException;
import com.revature.exception.RegistrationException;
import com.revature.exception.UserNotFoundException;
import com.revature.model.User;
import com.revature.service.UserService;
import com.revature.template.LoginTemplate;
import com.revature.template.MessageTemplate;
import com.revature.template.RegisterTemplate;
import com.revature.template.UpdateUserTemplate;

import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private HttpServletRequest request;

	@PostMapping(path = "login")
	public ResponseEntity<Object> login(@RequestBody @Valid LoginTemplate loginTemplate)
			throws BadParameterException, UserNotFoundException {

		User user = userService.login(loginTemplate.getUsername(), loginTemplate.getPassword());
		
		HttpSession session = request.getSession(true);
		user.addToSession(session);

		return ResponseEntity.status(200).body(new MessageTemplate("Successfully logged in"));

	}

	@PostMapping(path = "register")
	public ResponseEntity<Object> register(@RequestBody @Valid RegisterTemplate registerTemplate) throws BadParameterException, RegistrationException {

		User user = userService.register(registerTemplate.getUsername(), registerTemplate.getPassword(),
				registerTemplate.getEmail(), registerTemplate.getFirstName(), registerTemplate.getLastName(),
				registerTemplate.getRole());

		HttpSession session = request.getSession(true);
		user.addToSession(session);

		return ResponseEntity.status(201).body(new MessageTemplate("Successfully registered user"));

	}

	@PostMapping(path = "logout")
	@LoggedInOnly
	public ResponseEntity<Object> logout() {

		HttpSession session = request.getSession(true);
		session.invalidate();
		return ResponseEntity.status(200).body(new MessageTemplate("Successfully logged out user"));

	}

	@PutMapping(path = "user/{id}")
	@LoggedInOnly
	public ResponseEntity<Object> updateUserInfo(@RequestBody @Valid UpdateUserTemplate updateUserTemplate, @PathVariable("id") int id) throws BadParameterException {

		User user = userService.updateUserInfo(id, updateUserTemplate.getUsername(), updateUserTemplate.getPassword(),
				updateUserTemplate.getEmail(), updateUserTemplate.getFirstName(), updateUserTemplate.getLastName());

		HttpSession session = request.getSession(true);
		user.addToSession(session);

		return ResponseEntity.status(200).body(new MessageTemplate("Successfully updated user information"));

	}

}
