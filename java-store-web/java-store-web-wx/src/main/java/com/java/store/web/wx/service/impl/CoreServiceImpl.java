package com.java.store.web.wx.service.impl;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.java.store.web.wx.service.CoreService;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

/**
 * @author zeronx
 * @date 2016年8月31日下午10:49:32
 * @version v1.0
 */
@Service
public class CoreServiceImpl implements CoreService{

	@Value("${wx.secure.token}")
	private String sToken;
	@Value("${wx.secure.corpID}")
	private String sCorpID;
	@Value("${wx.secure.encodingAESKey}")
	private String sEncodingAESKey;
	
	@Override
	public String processGet(String signature, String timestamp, String nonce, String echostr) {
		String sEchoStr =""; //需要返回的明文
		try {
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
			sEchoStr = wxcpt.VerifyURL(signature, timestamp, nonce, echostr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sEchoStr;
	}

	@Override
	public String processPostMsg(String signature, String timestamp, String nonce, String requestData) {
		String sEncryptMsg = "";
		System.out.println("===================================== ");
		try {
			WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
			System.out.println("++++++++++++++++++++++++++++++++++");
			String sMsg = wxcpt.DecryptMsg(signature, timestamp, nonce, requestData);
			System.out.println("=======================after decrypt msg: " + sMsg);
			// TODO: 解析出明文xml标签的内容进行处理
			// For example:
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(sMsg);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);
			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("Content");
			String Content = nodelist1.item(0).getTextContent();
			System.out.println("Content：" + Content);
			String sRespData = "<xml>"
					+ "<ToUserName><![CDATA[mycreate]]></ToUserName>"
					+ "<FromUserName><![CDATA[wx6bb4e04c14be9275]]></FromUserName>"
					+ "<CreateTime>"+timestamp+"</CreateTime>"
					+ "<MsgType><![CDATA[text]]></MsgType>"
					+ "<Content><![CDATA[this is a test]]></Content>"
					+ "<MsgId>1234567890123458</MsgId>"
					+ "<AgentID>2</AgentID>"
					+ "</xml>";
			sEncryptMsg = wxcpt.EncryptMsg(sRespData, timestamp, nonce);
			System.out.println("=======================after encrypt sEncrytMsg: " + sEncryptMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sEncryptMsg;
	}

}
