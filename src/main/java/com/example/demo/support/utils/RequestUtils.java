/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.utils;

import com.example.demo.support.enums.HttpEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 13:47
 */
@Log4j2
public class RequestUtils {
	
	public static boolean isGet(HttpServletRequest request) {
		//将字符串与指定的对象比较，不考虑大小写
		return HttpEnum.Header.GET.toString().equalsIgnoreCase(request.getMethod());
	}
	
	public static boolean isPost(HttpServletRequest request) {
		return HttpEnum.Header.POST.toString().equalsIgnoreCase(request.getMethod());
	}
	
	public static boolean isPut(HttpServletRequest request) {
		return HttpEnum.Header.PUT.toString().equalsIgnoreCase(request.getMethod());
	}
	
	public static boolean isDelete(HttpServletRequest request) {
		return HttpEnum.Header.DELETE.toString().equalsIgnoreCase(request.getMethod());
	}
	
	public static boolean isTrace(HttpServletRequest request) {
		return HttpEnum.Header.TRACE.toString().equalsIgnoreCase(request.getMethod());
	}
	
	public static boolean isHead(HttpServletRequest request) {
		return HttpEnum.Header.HEAD.toString().equalsIgnoreCase(request.getMethod());
	}
	
	public static boolean isPatch(HttpServletRequest request) {
		return HttpEnum.Header.PATCH.toString().equalsIgnoreCase(request.getMethod());
	}
	
	public static boolean isOptions(HttpServletRequest request) {
		return HttpEnum.Header.OPTIONS.toString().equalsIgnoreCase(request.getMethod());
	}
	
	public static byte[] getByteBody(HttpServletRequest request) {
		byte[] body = new byte[0];
		try {
			body = StreamUtils.copyToByteArray(request.getInputStream());
			return body;
		} catch (IOException e) {
			log.error("Error:get request body byte[] fail," + e);
		}
		return body;
	}
	
	
}
