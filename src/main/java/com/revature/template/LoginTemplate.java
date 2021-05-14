package com.revature.template;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class LoginTemplate {
	
	@NotBlank
	private String username;
	@NotBlank
	private String password;

}
