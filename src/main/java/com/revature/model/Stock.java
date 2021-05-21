package com.revature.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames= {"name"}),
		@UniqueConstraint(columnNames= {"symbol"})
})
@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="stock_id")
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
	
	@NotBlank
	@OneToMany(mappedBy="stock", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonBackReference
    private Set<UserStock> mappings;

}
