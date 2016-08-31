package com.java.store.web.test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.java.store.web.dubbo.provider.client.MyFirstDubboService;
import com.java.store.web.test.template.BaseTestTemplate;

/**
 * @author zeronx
 * @date 2016年6月18日下午8:52:32
 * @version v1.0
 */
public class TestDubbo extends BaseTestTemplate{

	@Autowired
	private MyFirstDubboService myFirstDubboService;
	
	@Test
	public void testDubbo() throws IOException {
		System.out.println(myFirstDubboService);
		System.out.println("任意键退出");
		System.in.read();
	}
}
