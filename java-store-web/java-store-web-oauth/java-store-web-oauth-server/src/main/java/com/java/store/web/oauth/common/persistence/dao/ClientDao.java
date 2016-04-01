package com.java.store.web.oauth.common.persistence.dao;

import com.java.store.web.oauth.common.persistence.entity.Client;

public interface ClientDao extends BaseRepository<Client> {

	Client findByClientId(String clientId);

}
