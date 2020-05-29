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
		GET("GET", "向指定的URL（URI）请求资源（资源文件或是数据均可）"),
		POST("POST", "向指定的URL（URI）提交数据"),
		PUT("PUT", "功能跟post相似，用来将信息放到请求的URL（URI）上"),
		DELETE("DELETE", "用于删除请求URL上的某个资源"),
		TRACE("TRACE", "回显服务器收到的请求，主要用于测试或诊断。"),
		PATCH("CONNECT", "HTTP/1.1协议中预留给能够将连接改为管道方式的代理服务器。"),
		HEAD("HEAD", "类似于get请求，只不过返回的响应中没有具体的内容，用于获取报头"),
		OPTIONS("OPTIONS", "允许客户端查看服务器的性能");
		private String value;
		private String description;
	}
	
	
}
