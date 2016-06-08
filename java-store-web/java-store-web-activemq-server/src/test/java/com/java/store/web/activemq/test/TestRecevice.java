package com.java.store.web.activemq.test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.java.store.web.activemq.server.ReceiveService2;
import com.java.store.web.activemq.test.Template.BaseTestTemplate;
import com.sun.tools.javac.parser.Scanner;

/**
 * @author zeronx
 * @date 2016年6月2日下午10:06:50
 * @version v1.0
 */
public class TestRecevice extends BaseTestTemplate {
	
	@Autowired
	private ReceiveService2 receiveService;
	
	@Test
	public void testReceive() {
		 try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(receiveService);
	}
}
