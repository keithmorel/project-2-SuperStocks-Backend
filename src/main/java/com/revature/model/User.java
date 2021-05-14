package com.revature.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Users")
@Data @NoArgsConstructor @AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="username", length=50, unique=true, nullable=false)
	private String username;
	
	@Column(name="password", length=50, nullable=false)
	private String password;
	
	@Column(name="email", length=50, unique=true, nullable=false)
	private String email;
	
	@Column(name="first_name", length=50, nullable=false)
	private String firstName;
	
	@Column(name="last_name", length=50, nullable=false)
	private String lastName;
	
	@ManyToOne
	@JoinColumn(name="role_id", nullable=false)
	private UserRole userRole;

}
