package com.springsecurity.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="usertab")
public class User {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String username;
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="rolestab")
	
	@Column(name="role")
	private Set<String> roles;

	
	
	
	  public User(Integer id, String username, String password, Set<String> roles)
	  { super(); this.id = id; this.username = username; this.password = password;
	  this.roles = roles; }
	  
	  
	  
	  public User() {
	  
	  }
	  
	  
	  public Integer getId() { return id; }
	  
	  
	  
	  public void setId(Integer id) { this.id = id; }
	  
	  
	  
	  public String getUsername() { return username; }
	  
	  
	  
	  public void setUsername(String username) { this.username = username; }
	  
	  
	  
	  public String getPassword() { return password; }
	  
	  
	  
	  public void setPassword(String password) { this.password = password; }
	  
	  
	  
	  public Set<String> getRoles() { return roles; }
	  
	  
	  
	  public void setRoles(Set<String> roles) { this.roles = roles; }
	  
	  
	  @Override public String toString() { return "User [id=" + id + ", username="
	  + username + ", password=" + password + ", roles=" + roles + "]"; }
	 

	
	

}
