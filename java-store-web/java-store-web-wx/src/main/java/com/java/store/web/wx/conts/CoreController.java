package com.java.store.web.wx.conts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.store.web.wx.service.CoreService;
import com.sun.tools.javac.resources.compiler;

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
	public String receiveValidator(@RequestParam("msg_signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
		String result = coreService.processGet(signature, timestamp, nonce, echostr);
		return null;
	}
	
	@RequestMapping(value="receive", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String receiveMessage(@RequestParam("msg_signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestBody String requestData) {
		
		return null;
	}
}
