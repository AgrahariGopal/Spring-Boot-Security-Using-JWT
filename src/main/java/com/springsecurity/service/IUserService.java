package com.springsecurity.service;

import java.util.Optional;

import com.springsecurity.model.User;


public interface IUserService {
		
	Integer saveUser(User user);
	Optional<User> findByUsername(String username);
}
