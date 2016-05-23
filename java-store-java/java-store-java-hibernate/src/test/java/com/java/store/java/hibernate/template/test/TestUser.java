package com.java.store.java.hibernate.template.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.java.store.java.hibernate.dao.UserDao;
import com.java.store.java.hibernate.template.BaseTestTemplate;

/**
* @author ltao
* @version 1.0 创建时间：2016年5月20日 上午11:32:03
* 
*/
public class TestUser extends BaseTestTemplate {

	@Autowired
	private UserDao userDao;
	
	@Test
	public void test() {
		System.out.println(userDao);
	}
}
