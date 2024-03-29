package com.poseidon.util;

import javax.servlet.http.HttpServletRequest;

public class Util {

	public static int str2Int(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				sb.append(str.charAt(i));
			}
		}
		int number = 0;
		if (sb.length() > 0) {
			number = Integer.parseInt(sb.toString());
		}
		// System.out.println("변환된 숫자 " + number);
		return number;
	}

	public static int str2Int2(String str) {
		String numberOnly = str.replaceAll("[^0-9]", "");
		return Integer.parseInt(numberOnly);
	}

	public static boolean intCheck(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean intCheck2(String str) {
		boolean result = true;
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				result = false;
				break;
			}
		}
		return result;
	}

	// ip가져오기
	// IP얻어오기
	public static String getIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");
		//System.out.println("X-FORWARDED-FOR : " + ip);
		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
			//System.out.println("Proxy-Client-IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			//System.out.println("WL-Proxy-Client-IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			//System.out.println("HTTP_CLIENT_IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			//System.out.println("HTTP_X_FORWARDED_FOR : " + ip);
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
			//System.out.println("getRemoteAddr : " + ip);
		}
		return ip;
	}

	// HTML태그를 특수기호로 변경하기 < &lt > &gt
	public static String removeTag(String str) {
		str = str.replaceAll("<", "&lt");
		str = str.replaceAll(">", "&gt");
		return str;
	}

	// 엔터키 처리
	public static String addBR(String str) {
		return str.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
	}

	// 아이피 중간을 ♡로 가리기 172.30.1.27 -> 172.♡.1.27
	public static String ipMasking(String ip) {
		if (ip.indexOf('.') != -1) {
			StringBuffer sb = new StringBuffer();// 멀티스레드 환경에서도 동기화 지원
			sb.append(ip.substring(0, ip.indexOf('.')+1));
			sb.append("♡");
			sb.append(ip.substring(ip.indexOf('.',ip.indexOf('.')+1)));
			return sb.toString();
		} else {
			return ip;
		}
	}

}
