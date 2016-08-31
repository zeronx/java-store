package com.java.store.java.wx.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
/**
 * 核心请求处理类
 * 
 * @date 2013-05-18
 */
public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = 4440739483644821986L;

	private String sToken = "weixinCourse";
	private String sEncodingAESKey = "Tt4O9lUVFIzMpOwhHa4HpzUHGSh8gTHocRFJe3uquPf";
	private String sCorpID = "wx6bb4e04c14be9275";
	
	/**
	 * 确认请求来自微信服务器
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名
		String sVerifyMsgSig = URLDecoder.decode(request.getParameter("msg_signature"),"UTF-8");
		// 时间戳
		String sVerifyTimeStamp = URLDecoder.decode(request.getParameter("timestamp"),"UTF-8");
		// 随机数
		String sVerifyNonce = URLDecoder.decode(request.getParameter("nonce"),"UTF-8");
		// 随机字符串
		String sVerifyEchoStr = URLDecoder.decode(request.getParameter("echostr"),"UTF-8");
		System.out.println( "msg = " + sVerifyMsgSig + " timetamp = " + sVerifyTimeStamp + " nonce = " +sVerifyNonce + " echostr = " + sVerifyEchoStr );
		WXBizMsgCrypt wxcpt = null;
		try {
			wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
		} catch (AesException e1) {
			e1.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
//		if (SignUtil.checkSignature(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce)) {
////			out.print(sVerifyEchoStr);
//		}
		String sEchoStr; //需要返回的明文
		try {
			sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp,sVerifyNonce, sVerifyEchoStr);
			System.out.println("verifyurl echostr: " + sEchoStr);
			out.print(sEchoStr);
			// 验证URL成功，将sEchoStr返回
			// HttpUtils.SetResponse(sEchoStr);
		} catch (Exception e) {
			//验证URL失败，错误原因请查看异常
			e.printStackTrace();
		}
		out.close();
		out = null;
	}

	/**
	 * 处理微信服务器发来的消息
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// post请求的密文数据
		InputStream is = null;     
	    String sReqData = "";     
	    try {  
	        is = request.getInputStream();         
	        sReqData = IOUtils.toString(is, "utf-8");     
	        } catch (IOException e) {
	            e.printStackTrace();     
	    } 
//	    String sReqData = "<xml><ToUserName><![CDATA[wx5823bf96d3bd56c7]]></ToUserName><Encrypt><![CDATA[RypEvHKD8QQKFhvQ6QleEB4J58tiPdvo+rtK1I9qca6aM/wvqnLSV5zEPeusUiX5L5X/0lWfrf0QADHHhGd3QczcdCUpj911L3vg3W/sYYvuJTs3TUUkSUXxaccAS0qhxchrRYt66wiSpGLYL42aM6A8dTT+6k4aSknmPj48kzJs8qLjvd4Xgpue06DOdnLxAUHzM6+kDZ+HMZfJYuR+LtwGc2hgf5gsijff0ekUNXZiqATP7PF5mZxZ3Izoun1s4zG4LUMnvw2r+KqCKIw+3IQH03v+BCA9nMELNqbSf6tiWSrXJB3LAVGUcallcrw8V2t9EL4EhzJWrQUax5wLVMNS0+rUPA3k22Ncx4XXZS9o0MBH27Bo6BpNelZpS+/uh9KsNlY6bHCmJU9p8g7m3fVKn28H3KDYA5Pl/T8Z1ptDAVe0lXdQ2YoyyH2uyPIGHBZZIs2pDBS8R07+qN+E7Q==]]></Encrypt><AgentID><![CDATA[218]]></AgentID></xml>";
		// String sReqMsgSig = HttpUtils.ParseUrl("msg_signature");
		String sReqMsgSig = URLDecoder.decode(request.getParameter("msg_signature"),"UTF-8");
		// String sReqTimeStamp = HttpUtils.ParseUrl("timestamp");
		String sReqTimeStamp = URLDecoder.decode(request.getParameter("timestamp"),"UTF-8");
		// String sReqNonce = HttpUtils.ParseUrl("nonce");
		String sReqNonce = URLDecoder.decode(request.getParameter("nonce"),"UTF-8");
		WXBizMsgCrypt wxcpt = null;
		System.out.println( "sreqdata = " +sReqData +"msg_sige = "+ sReqMsgSig + " stime = " + sReqTimeStamp + " snoce = " + sReqNonce);
		try {
			wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
		} catch (AesException e1) {
			e1.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		try {
			String sMsg = wxcpt.DecryptMsg(sReqMsgSig, sReqTimeStamp, sReqNonce, sReqData);
			System.out.println("after decrypt msg: " + sMsg);
			// TODO: 解析出明文xml标签的内容进行处理
			// For example:
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(sMsg);
			InputSource iso = new InputSource(sr);
			Document document = db.parse(iso);
			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("Content");
			String Content = nodelist1.item(0).getNodeValue();
//					String Content = nodelist1.item(0).getTextContent();
			System.out.println("Content：" + Content);
			
		} catch (Exception e) {
			// 解密失败，失败原因请查看异常
			e.printStackTrace();
		}
	}

}
