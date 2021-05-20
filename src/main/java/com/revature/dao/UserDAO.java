package com.revature.dao;

import java.util.HashSet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.model.User;
import com.revature.model.UserRole;
import com.revature.util.HashPassword;

@Repository
public class UserDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	// Next two methods for testing
	
	@Transactional
	public UserRole getRoleById(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		return session.get(UserRole.class, id);
		
	}
	
	@Transactional
	public UserRole addUserRole(int id, String role) {
		
		Session session = sessionFactory.getCurrentSession();
		
		UserRole userRole = new UserRole(id, role);
		
		session.save(userRole);
		
		return userRole;
		
	}

	@Transactional
	public User getUserByUsernameAndPassword(String username, String password) {

		Session session = sessionFactory.getCurrentSession();
		String hashedPassword = HashPassword.hashPassword(password);

		return session.createQuery("FROM User WHERE username=:un AND password=:pw", User.class)
				.setParameter("un", username).setParameter("pw", hashedPassword).getSingleResult();

	}

	@Transactional
	public User registerNewUser(String username, String password, String email, String firstName, String lastName, int roleId) {

		Session session = sessionFactory.getCurrentSession();
		String hashedPassword = HashPassword.hashPassword(password);

		UserRole role = session.get(UserRole.class, roleId);

		User newUser = new User(0, username, hashedPassword, email, firstName, lastName, role, new HashSet<>());

		session.persist(newUser);

		return newUser;

	}

	@Transactional
	public User updateUser(int id, String username, String password, String email, String firstName, String lastName) {

		Session session = sessionFactory.getCurrentSession();

		User userToUpdate = session.get(User.class, id);
		if (!userToUpdate.getPassword().equals(password)) {
			password = HashPassword.hashPassword(password);
		}
		
		userToUpdate.setUsername(username);
		userToUpdate.setPassword(password);
		userToUpdate.setEmail(email);
		userToUpdate.setFirstName(firstName);
		userToUpdate.setLastName(lastName);

		session.persist(userToUpdate);
		return userToUpdate;

	}

}
