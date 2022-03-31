package com.springsecurity.service.impl;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.model.User;
import com.springsecurity.repo.UserRepository;
import com.springsecurity.service.IUserService;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService {

	@Autowired
	private UserRepository repo; //HAS -A
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	//get user by username
	@Override
	public Integer saveUser(User user) 
	{
		//Encode Password
		
		user.setPassword(pwdEncoder.encode(user.getPassword()));
	    return repo.save(user).getId();
	}
	
	@Override
	public Optional<User> findByUsername(String username)
	{
		return repo.findByUsername(username);
	}

	
	//*************************************************
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		Optional<User> opt = findByUsername(username);
		
		if(opt.isEmpty())
			throw new UsernameNotFoundException("User not exit");
			
			// read user from(DB)
		   User user = opt.get();
		return new org.springframework.security.core.userdetails.User(
				username,
				user.getPassword(),
				user.getRoles().stream()
				.map(role->new SimpleGrantedAuthority(role))
				.collect(Collectors.toList())
				);
		
	}
	
}
