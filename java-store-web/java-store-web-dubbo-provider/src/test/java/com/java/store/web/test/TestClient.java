package com.java.store.web.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.java.store.web.dubbo.provider.client.MyFirstDubboService;

/**
 * @author zeronx
 * @date 2016年6月18日下午9:17:26
 * @version v1.0
 */
public class TestClient {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("java-store-web-dubbo-consumer.xml");
		context.start();
		MyFirstDubboService hellService = (MyFirstDubboService)context.getBean("dubboService"); // 获取远程服务代理
        String res = hellService.sayHelloTo("world"); // 执行远程方法
        System.out.println( res ); // 显示调用结果
	}
}
