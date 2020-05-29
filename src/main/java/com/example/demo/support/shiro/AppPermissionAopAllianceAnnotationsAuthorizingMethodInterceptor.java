/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro;

import org.apache.shiro.spring.aop.SpringAnnotationResolver;
import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor;

/**
 * 自定义注解的AOP拦截器
 *
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 14:22
 */
public class AppPermissionAopAllianceAnnotationsAuthorizingMethodInterceptor extends AopAllianceAnnotationsAuthorizingMethodInterceptor {
	
	public AppPermissionAopAllianceAnnotationsAuthorizingMethodInterceptor() {
		super();
		this.methodInterceptors.add(new AppPermissionAnnotationMethodThterceptor(new SpringAnnotationResolver()));
	}
	
}
