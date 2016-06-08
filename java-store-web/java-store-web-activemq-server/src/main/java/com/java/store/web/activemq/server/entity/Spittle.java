package com.java.store.web.activemq.server.entity;

import java.io.Serializable;

/**
 * @author zeronx
 * @date 2016年6月2日下午9:53:20
 * @version v1.0
 */
public class Spittle implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String name;
	
	private Integer age;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
}
