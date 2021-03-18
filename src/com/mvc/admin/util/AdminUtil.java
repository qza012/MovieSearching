package com.mvc.admin.util;

import javax.servlet.http.HttpServletRequest;

public class AdminUtil {
	
	public static void log(Object... arrObj) {
		// 동기화 O
		StringBuffer sb = new StringBuffer();
		for(Object obj : arrObj) {
			sb.append(obj);
			sb.append(" / ");
		}
		System.out.println(sb);
	}
	
	public static boolean IsLogin(HttpServletRequest req) {
		return req.getSession().getAttribute("myLoginId") != null ? true : false;
	}
}
