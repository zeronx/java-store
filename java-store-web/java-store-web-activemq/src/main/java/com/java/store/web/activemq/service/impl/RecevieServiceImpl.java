package com.java.store.web.activemq.service.impl;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.java.store.web.activemq.entity.Spittle;
/**
 * @author zeronx
 * @date 2016年6月2日下午10:01:55
 * @version v1.0
 */
@Component
public class RecevieServiceImpl {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public Spittle getSpitle() {
		try {
			ObjectMessage objectMessage = (ObjectMessage) jmsTemplate.receive();
			System.out.println(JSON.toJSONString((Spittle)objectMessage.getObject()));
			return (Spittle) objectMessage.getObject();
		} catch (JMSException e) {
			throw JmsUtils.convertJmsAccessException(e);
		}
	}
}
