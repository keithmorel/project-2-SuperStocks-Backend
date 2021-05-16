package com.revature.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.dao.AdminDAO;
import com.revature.exception.UserNotFoundException;
import com.revature.model.User;

@Service
public class AdminService {
	
	@Autowired
	private AdminDAO adminDAO;

	@Transactional(rollbackFor = { UserNotFoundException.class })
	public User getUserById(int id) throws UserNotFoundException {
		
		try {
			return adminDAO.getUser(id);
		}catch(NoResultException e) {
			throw new UserNotFoundException("User does not exist.");
		}
	}
	
	@Transactional(rollbackFor = { UserNotFoundException.class })
	public List<User> getAllUser() throws UserNotFoundException {
		
		try {
			return adminDAO.getUsers();
		}catch(NoResultException e) {
			throw new UserNotFoundException("User does not exist.");
		}
	}
	
	
}
