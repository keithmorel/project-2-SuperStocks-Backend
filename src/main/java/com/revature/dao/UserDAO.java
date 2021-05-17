package com.revature.dao;

import java.util.HashSet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.model.User;
import com.revature.model.UserRole;
import com.revature.model.User_Stock;
import com.revature.util.HashPassword;

@Repository
public class UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public User getUserByUsernameAndPassword(String username, String password) {

		Session session = sessionFactory.getCurrentSession();
		String hashedPassword = HashPassword.hashPassword(password);

		User user = (User) session.createQuery("FROM User WHERE username=:un AND password=:pw")
				.setParameter("un", username).setParameter("pw", hashedPassword).getSingleResult();

		return user;

	}

	@Transactional
	public User registerNewUser(String username, String password, String email, String firstName, String lastName, int roleId) {

		Session session = sessionFactory.getCurrentSession();
		String hashedPassword = HashPassword.hashPassword(password);

		UserRole role = session.get(UserRole.class, roleId);

		User newUser = new User(0, username, hashedPassword, email, firstName, lastName, role, new HashSet<User_Stock>());

		session.persist(newUser);

		return newUser;

	}

	@Transactional
	public User updateUser(int id, String username, String password, String email, String firstName, String lastName) {

		Session session = sessionFactory.getCurrentSession();
		String hashedPassword = HashPassword.hashPassword(password);

		User userToUpdate = session.get(User.class, id);
		userToUpdate.setUsername(username);
		userToUpdate.setPassword(hashedPassword);
		userToUpdate.setEmail(email);
		userToUpdate.setFirstName(firstName);
		userToUpdate.setLastName(lastName);

		session.persist(userToUpdate);
		return userToUpdate;

	}

}
