package com.revature.app;

import org.hibernate.Session;

import com.revature.model.User;
import com.revature.model.UserRole;
import com.revature.util.SessionUtility;

public class Application {
	
	public static void main(String[] args) {
	
		Session session = SessionUtility.getSessionFactory().openSession();
		
		UserRole userRole = session.get(UserRole.class, 1);
		UserRole adminRole = session.get(UserRole.class, 2);
		
		System.out.println(userRole);
		System.out.println(adminRole);
		
		User user = session.get(User.class, 1);
		User admin = session.get(User.class, 2);
		
		System.out.println(user);
		System.out.println(user.getUserRole().getRoleName());
		System.out.println(admin.getUsername());
		
	}

}
