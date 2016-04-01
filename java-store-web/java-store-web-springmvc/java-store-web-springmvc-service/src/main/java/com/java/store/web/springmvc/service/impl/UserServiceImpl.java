package com.java.store.web.springmvc.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.java.store.web.springmvc.service.UserService;

/**
 * @author Dell
 * @date 2016年3月27日 下午6:54:27
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService{

	@Value("${user.name}")
	private String name;
	
	public UserServiceImpl() {
		System.out.println(name);
		System.out.println("userServiceImpl construtor create ....");
	}
	@Override
	public String getUserInfo(Integer userId) {
		System.out.println(name);
		System.out.println("success into UserServiceImpl");
		return null;
	}

}
