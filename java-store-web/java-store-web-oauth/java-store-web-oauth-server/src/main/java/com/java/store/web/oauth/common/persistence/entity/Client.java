package com.java.store.web.oauth.common.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_oauth_client")
@SuppressWarnings("serial")
public class Client extends BaseEntity {

	private String clientId;
	private String clientName;
	private String clientSecret;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
}
