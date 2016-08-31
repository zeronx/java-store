package com.java.store.web.wx.service;
/**
 * @author zeronx
 * @date 2016年8月31日下午10:49:32
 * @version v1.0
 */
public interface CoreService {

	String processGet(String signature, String timestamp, String nonce, String echostr);

}
