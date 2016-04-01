package com.java.store.web.oauth.server.server;

import com.java.store.web.oauth.common.persistence.entity.User;

public interface UserService {

	User findByUsername(String username);

}
