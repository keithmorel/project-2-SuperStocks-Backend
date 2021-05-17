package com.revature.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.model.User;

@Repository
public class AdminDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public User getUser(int id) {

		Session session = sessionFactory.getCurrentSession();

		User user = (User) session.createQuery("FROM User WHERE id=:id")
				.setParameter("id", id)
				.getSingleResult();

		return user;
	}

	@Transactional
	public List<User> getUsers() {
		Session session = sessionFactory.getCurrentSession();

		List<User> users = new ArrayList<User>();
    
		users = (List<User>) session.createQuery("FROM User", User.class)
				.getResultList();

		return users;
	}

}
