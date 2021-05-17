package com.revature.template;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class StockTemplate {
	
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
