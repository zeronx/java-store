package com.java.store.web.wx.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyHttpUtils {

	public static void writeMsg(HttpServletRequest request, HttpServletResponse response, String result) {
	    try {
	    	PrintWriter out = response.getWriter();
	    	out.print(result);
	    	out.close();
	    } catch (IOException e) {
		}  
	}

}
