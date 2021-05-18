package com.revature.dao;

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

		return session.createQuery("FROM User WHERE id=:id", User.class)
				.setParameter("id", id)
				.getSingleResult();
		
	}

	@Transactional
	public List<User> getUsers() {
		Session session = sessionFactory.getCurrentSession();
    
		return session.createQuery("FROM User", User.class)
				.getResultList();

	}

}
