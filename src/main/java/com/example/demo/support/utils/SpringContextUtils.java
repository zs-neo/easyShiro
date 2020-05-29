/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/29 10:42
 */
@Log4j2
@Component
public class SpringContextUtils {
	
	private static ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringContextUtils.context = context;
	}
	
	public static ApplicationContext getContext() {
		log.info("get application context");
		return context;
	}
	
}
