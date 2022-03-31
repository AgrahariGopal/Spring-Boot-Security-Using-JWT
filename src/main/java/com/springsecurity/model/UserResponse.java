package com.springsecurity.model;

import lombok.Data;

@Data
public class UserResponse {
	
	private String token;

	public UserResponse() {
	}

	public UserResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "UserResponse [token=" + token + "]";
	}
	
	

}
