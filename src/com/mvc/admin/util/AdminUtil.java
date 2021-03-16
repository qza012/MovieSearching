package com.mvc.admin.util;

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
	
}
