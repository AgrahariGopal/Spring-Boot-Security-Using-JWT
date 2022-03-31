package com.springsecurity.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springsecurity.util.JwtUtil;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil util;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//read token from authorization header
		
		String token = request.getHeader("Authorization");
		if(token!=null)
		{
			// do validation 
			String username = util.getUsername(token);
			
			//username should not be empty, context-auth must be empty****************
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
				UserDetails usr = userDetailsService.loadUserByUsername(username);
				
				//validate token
				boolean isValid = util.validateToken(token, usr.getUsername());
				
				if(isValid)
				{
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
							new UsernamePasswordAuthenticationToken(
									username, usr.getPassword(), usr.getAuthorities());
					
					usernamePasswordAuthenticationToken.setDetails(
							new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			
		}
		filterChain.doFilter(request, response);
		
	}

}
