package com.java.store.java.hibernate.dao;

import com.java.store.java.hibernate.entity.User;

/**
* @author ltao
* @version 1.0 创建时间：2016年5月20日 上午11:40:39
* 
*/
public interface UserDao extends GenericDao<User, Integer>{

	public User getUserByPro(final String proName, final Object[] params, final Class clazz);
	
}
