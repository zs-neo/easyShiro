/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.enums;

import lombok.AllArgsConstructor;

/**
 * HTTP相关枚举类
 *
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 13:47
 */
public interface HttpEnum {
	
	@AllArgsConstructor
	enum Header {
		GET("GET", ""),
		POST("POST", ""),
		PUT("PUT", ""),
		DELETE("DELETE", ""),
		TRACE("TRACE", ""),
		PATCH("PATCH", ""),
		HEAD("HEAD", ""),
		OPTIONS("OPTIONS", "");
		private String value;
		private String description;
	}
	
	
}
