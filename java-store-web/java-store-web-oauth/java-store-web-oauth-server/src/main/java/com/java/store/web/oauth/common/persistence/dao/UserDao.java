package com.java.store.web.oauth.common.persistence.dao;

import com.java.store.web.oauth.common.persistence.entity.User;

public interface UserDao extends BaseRepository<User> {

	User findByUsername(String username);

}
