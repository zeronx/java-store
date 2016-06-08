package com.java.store.web.activemq.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.java.store.web.activemq.entity.Spittle;
import com.java.store.web.activemq.service.AlertService;

/**
 * @author zeronx
 * @date 2016年6月2日下午9:51:48
 * @version v1.0
 */
@Component
public class AlertServiceImpl implements AlertService {
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Override
	public void sendSpitterAlert(final Spittle spittle) {
		jmsTemplate.send("spittle.queue", new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(spittle);
			}
		});
	}
}
