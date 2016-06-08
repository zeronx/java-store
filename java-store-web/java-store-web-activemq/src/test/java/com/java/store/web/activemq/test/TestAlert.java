package com.java.store.web.activemq.test;

import org.springframework.beans.factory.annotation.Autowired;

import com.java.store.web.activemq.entity.Spittle;
import com.java.store.web.activemq.service.AlertService;
import com.java.store.web.activemq.test.Template.BaseTestTemplate;

/**
 * @author zeronx
 * @date 2016年6月2日下午10:06:50
 * @version v1.0
 */
public class TestAlert extends BaseTestTemplate {
	
	@Autowired
	private AlertService alertService;
	
	@org.junit.Test
	public void testSendAlertSpittle() {
		Spittle spittle = new Spittle();
		spittle.setAge(12);
		spittle.setId(12);
		spittle.setName("test");
		alertService.sendSpitterAlert(spittle);
	}
	 
}
