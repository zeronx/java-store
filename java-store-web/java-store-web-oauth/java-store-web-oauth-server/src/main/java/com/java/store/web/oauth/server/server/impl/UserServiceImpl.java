package com.java.store.web.oauth.server.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.store.web.oauth.common.persistence.dao.UserDao;
import com.java.store.web.oauth.common.persistence.entity.User;
import com.java.store.web.oauth.server.server.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

}
