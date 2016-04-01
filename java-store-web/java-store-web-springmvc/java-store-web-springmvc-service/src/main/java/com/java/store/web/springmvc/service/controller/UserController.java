package com.java.store.web.springmvc.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.store.web.springmvc.service.UserService;

/**
 * @author Dell
 * @date 2016年3月27日 下午6:57:30
 * @version 1.0
 */
@Controller
public class UserController {

    public UserController() {
    	System.out.println("UserController construtor create .....");
	}
	
	@Autowired
	private UserService userService;
	
	//@Path("get_user_info")
	@RequestMapping("/get_user_info")
	public String getUserById(Integer userId) {
		userService.getUserInfo(userId);
		return "success";
	}
}
