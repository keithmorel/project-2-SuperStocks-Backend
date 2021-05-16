package com.revature.service;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.dao.UserDAO;
import com.revature.exception.BadParameterException;
import com.revature.exception.RegistrationException;
import com.revature.exception.UserNotFoundException;
import com.revature.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Transactional(rollbackFor = { UserNotFoundException.class })
	public User login(String username, String password) throws BadParameterException, UserNotFoundException {
		
		if (username.trim().equals("") || password.trim().equals("")) {
			throw new BadParameterException("Username and password can't be blank");
		}
		
		try {
			return userDAO.getUserByUsernameAndPassword(username, password);
		} catch (NoResultException e) {
			throw new UserNotFoundException("Username or Password provided are incorrect");
		}
		
	}

	@Transactional(rollbackFor = { BadParameterException.class, RegistrationException.class })
	public User register(String username, String password, String email, String firstName, String lastName, String role) throws BadParameterException, RegistrationException {
		
		int roleId = 1;
		if (username.trim().equals("") || password.trim().equals("") || email.trim().equals("") || firstName.trim().equals("") ||
				lastName.trim().equals("") || role.trim().equals("")) {
			throw new BadParameterException("All info must be provided and not blank");
		} else if (!email.matches("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) {
			throw new BadParameterException("Email must be of the form '[email]@[website].[domain]'");
		} else if (role.equals("Admin")) {
			roleId = 2;
		} else if (!role.equals("User") && !role.equals("Admin")) {
			throw new BadParameterException("Role must be either User or Admin");
		}

		try {
			return userDAO.registerNewUser(username, password, email, firstName, lastName, roleId);
		} catch (PersistenceException e) {
			throw new RegistrationException("There is already an account with that username and email. Change one and try again.");
		}
		
	}

	@Transactional(rollbackFor = { BadParameterException.class })
	public User updateUserInfo(int id, String username, String password, String email, String firstName, String lastName) throws BadParameterException {

		if (username.trim().equals("") || password.trim().equals("") || email.trim().equals("") || firstName.trim().equals("") ||
				lastName.trim().equals("")) {
			throw new BadParameterException("All info must be provided and not blank");
		}
		
		return userDAO.updateUser(id, username, password, email, firstName, lastName);
		
	}

}
