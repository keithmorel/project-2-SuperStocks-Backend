package com.revature.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames= {"name"}),
		@UniqueConstraint(columnNames= {"symbol"})
})
@Data @NoArgsConstructor @AllArgsConstructor
public class Stock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String symbol;
	
	@NotBlank
	private String exchange;
	
	@NotBlank
	private Double price;
	
	@NotBlank
	private String type;

}
