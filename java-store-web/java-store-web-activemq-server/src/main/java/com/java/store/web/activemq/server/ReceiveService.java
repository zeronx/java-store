package com.java.store.web.activemq.server;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.java.store.web.activemq.server.entity.Spittle;

@Component
public class ReceiveService {

	public void process(Spittle user) {
		System.out.println(JSON.toJSONString(user));
	}
}
