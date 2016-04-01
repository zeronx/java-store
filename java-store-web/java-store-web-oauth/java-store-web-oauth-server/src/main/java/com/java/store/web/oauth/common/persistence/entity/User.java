package com.java.store.web.oauth.common.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="t_oauth_user")
@SuppressWarnings("serial")
public class User extends BaseEntity {

	private String username;
	private String password;
	private String salt;
	
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
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	@Transient
	public String getCredentialsSalt() {
		return username+salt;
	}
	
}
