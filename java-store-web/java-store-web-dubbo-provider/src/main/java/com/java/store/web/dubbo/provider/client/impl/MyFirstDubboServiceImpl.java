package com.java.store.web.dubbo.provider.client.impl;

import javax.ws.rs.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcContext;
import com.java.store.web.dubbo.provider.client.MyFirstDubboService;

/**
 * @author zeronx
 * @date 2016年6月18日下午8:30:56
 * @version v1.0
 */
@Service
public class MyFirstDubboServiceImpl implements MyFirstDubboService {

	private static Logger LOG = LoggerFactory.getLogger(MyFirstDubboServiceImpl.class);
	
	@POST
	
	@Override
	public String sayHelloTo(String name) {
		LOG.info("Hello {}", name);
		return "Hello " + name +"\n provider form s"+ RpcContext.getContext().getLocalAddress();
	}

}
