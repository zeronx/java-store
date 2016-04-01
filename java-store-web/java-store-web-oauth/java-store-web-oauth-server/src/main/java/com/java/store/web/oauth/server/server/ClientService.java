package com.java.store.web.oauth.server.server;

import com.java.store.web.oauth.common.persistence.entity.Client;

public interface ClientService {

	Client findByClientId(String clientId);

}
