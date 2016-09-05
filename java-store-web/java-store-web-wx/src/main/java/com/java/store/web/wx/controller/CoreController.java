package com.java.store.web.wx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.java.store.web.wx.service.CoreService;
import com.java.store.web.wx.utils.MyHttpUtils;

/**
 * @author zeronx
 * @date 2016年8月31日下午10:50:30
 * @version v1.0
 */
@Controller
@RequestMapping(value="wxapi")
public class CoreController {

	@Autowired
	private CoreService coreService;
	
	@RequestMapping(value="receive", method=RequestMethod.GET)
	public void receiveValidator(@RequestParam("msg_signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
		String result = coreService.processGet(signature, timestamp, nonce, echostr);
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
 		MyHttpUtils.writeMsg(request, response, result);
	}
	
	@RequestMapping(value="receive", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	public void receiveMessage(@RequestParam("msg_signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestBody String requestData) {
		System.out.println("=================requestData = ==========" + requestData);
		String result = coreService.processPostMsg(signature, timestamp, nonce, requestData);
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
 		MyHttpUtils.writeMsg(request, response, result);
	}
}
