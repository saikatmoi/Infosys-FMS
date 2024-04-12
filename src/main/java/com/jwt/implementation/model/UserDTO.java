package com.jwt.implementation.model;

import java.math.BigInteger;

public class UserDTO {

	private String userName;
    private String password;
    private String email;
    private String role;
	private BigInteger userPhone;


	public UserDTO(String userName, String password, String email, String role, BigInteger userPhone) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.role = role;
		this.userPhone = userPhone;
	}
	public String getUserName() {
		return userName;
	}
	public BigInteger getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(BigInteger userPhone) {
		this.userPhone = userPhone;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
    
}

