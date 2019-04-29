package com.iktpreobuka.final_project.entities.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginDTO {

	@NotBlank(message = "Username must be provided.")
	@Size(min=5, max=15, message = "Username must be between {min} and {max} characters long.")
	@Pattern(regexp = "^\\S*$", message = "Username must not contain white space.")
	private String username;

	@NotBlank(message = "Password must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Password must not contain white space.")
	@Size(min=5, max=10, message = "Password must be between {min} and {max} characters long.")
	private String password;
	
	public LoginDTO() {
		super();
	}
	
	public LoginDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}


	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
