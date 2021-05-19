package com.revature.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(uniqueConstraints= { 
		@UniqueConstraint(columnNames={"username", "email"}),
		@UniqueConstraint(columnNames= {"username"}),
		@UniqueConstraint(columnNames= {"email"})
})
@Data @NoArgsConstructor @AllArgsConstructor
public class User implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private int id;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	@ManyToOne
	@JoinColumn(name="roleId")
	private UserRole userRole;
	
	@NotBlank
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonBackReference
    private transient Set<UserStock> mappings;
	
	public void addToSession(HttpSession session) {
		
		session.setAttribute("loggedInUser", this);
		
	}

}
