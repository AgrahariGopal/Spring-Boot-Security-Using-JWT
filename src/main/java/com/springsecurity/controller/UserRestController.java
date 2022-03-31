package com.springsecurity.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.model.User;
import com.springsecurity.model.UserRequest;
import com.springsecurity.model.UserResponse;
import com.springsecurity.service.IUserService;
import com.springsecurity.util.JwtUtil;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	private IUserService service; // HAS-A
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	// save user data in database ****************************************
	@PostMapping("/save")
	public ResponseEntity<String> saveUser(@RequestBody User user)
	{
		Integer id = service.saveUser(user);
		String body="User '"+id+"' saved";
		return ResponseEntity.ok(body);
	}
	
	//validate user and generate token(login) ****************************************
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request)
	{
		// validate username/password with database
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(), request.getPassword()));
		
		String token = util.generateToken(request.getUsername());
		return ResponseEntity.ok(new UserResponse(token));
	}
 
	// after login only
	@PostMapping("/welcome")
	public ResponseEntity<String> accessData(Principal p)
	{
		return ResponseEntity.ok("Hello User!"+ p.getName());
	}
	
}
