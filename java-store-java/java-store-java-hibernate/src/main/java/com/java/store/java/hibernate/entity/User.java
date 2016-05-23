package com.java.store.java.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* @author ltao
* @version 1.0 创建时间：2016年5月20日 上午11:27:28
* 
*/
@SuppressWarnings("serial")
@Entity
@Table(name = "t_user")
public class User implements Serializable{

	private Integer id;
	
	private Integer age;
	
	private String name;
	
	private String lastName;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", age=" + age + ", name=" + name + ", lastName=" + lastName + "]";
	}
	
}
