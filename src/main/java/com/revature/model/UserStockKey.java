package com.revature.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class UserStockKey implements Serializable {
	
	@Column
	private int userId;
	
	@Column
	private int stockId;
	
}
