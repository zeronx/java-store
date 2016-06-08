package com.java.store.web.activemq.server;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.java.store.web.activemq.entity.Spittle;

@Component(value="messageListener")
public class ReceiveService2 implements MessageListener {


	public void onMessage(Message msg) {
		try {
			if (msg instanceof ObjectMessage) {
				ObjectMessage objMsg = (ObjectMessage) msg;
				Spittle user;
				user = (Spittle) objMsg.getObject();
				System.out.println(JSON.toJSONString(user));
			} else {
				System.out.println("unsupported Message type"
						+ msg.getJMSType());
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
