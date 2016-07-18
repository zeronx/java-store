package com.java.store.web.springmvc.service.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
	@RequestMapping(value = {"/get_user_info", "/get_user_info2"})
	public String getUserById(Integer userId) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		System.out.println("getContextPath = " + request.getContextPath());
		System.out.println("getHeadler = " + request.getHeader(""));
		System.out.println("getLocalAddr = " + request.getLocalAddr());
		System.out.println("getProtocol = " + request.getProtocol());
		System.out.println("RequestURL = " + request.getRequestURL());
		System.out.println("RequestURI = " + request.getRequestURI());
		System.out.println("ServletPath = " + request.getServletPath());
		System.out.println("getServletContext = " + request.getServletContext());
		System.out.println("getServletContext().getContextPath = " + request.getServletContext().getContextPath());
		System.out.println("getRealPath = " + request.getRealPath("/"));
		System.out.println("getServletContext().getRealPath = " + request.getServletContext().getRealPath("/"));
		userService.getUserInfo(userId);
		return "success";
	}
}
