package com.java.store.web.oauth.server.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.store.web.oauth.common.persistence.dao.ClientDao;
import com.java.store.web.oauth.common.persistence.entity.Client;
import com.java.store.web.oauth.server.server.ClientService;

@Component
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientDao clientDao;
	
	@Override
	public Client findByClientId(String clientId) {
		Client client = clientDao.findByClientId(clientId);
		if (client != null) {
			return client;
		}
		return null;
	}

}
