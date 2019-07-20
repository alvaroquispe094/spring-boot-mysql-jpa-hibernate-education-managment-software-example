package com.groupal.universia.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role implements Serializable{

	@Id  
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	  
	private String username;
	  
	private String role;
	  
	 
	  
	public Role() {}
	
	public Role(String usuario) {
	      this.setRole("ROLE_ADMIN");
	      this.setUsername(usuario);      
	}
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	  
	
	 

}
