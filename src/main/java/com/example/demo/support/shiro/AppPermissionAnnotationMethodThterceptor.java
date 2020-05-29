/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro;

import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;

/**
 * 自定义注解的方法拦截器
 *
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 13:47
 */
public class AppPermissionAnnotationMethodThterceptor extends AuthorizingAnnotationMethodInterceptor {
	
	public AppPermissionAnnotationMethodThterceptor(AuthorizingAnnotationHandler handler) {
		super(new AppPermissionAnnotationHandler());
	}
	
	public AppPermissionAnnotationMethodThterceptor(AnnotationResolver resolver) {
		super(new AppPermissionAnnotationHandler(), resolver);
	}
	
	@Override
	public void assertAuthorized(MethodInvocation mi) throws AuthorizationException {
		// 验证权限
		try {
			((AppPermissionAnnotationHandler) this.getHandler()).assertAuthorized(getAnnotation(mi));
		} catch (AuthorizationException ae) {
			if (ae.getCause() == null) {
				ae.initCause(new AuthorizationException("当前的方法没有通过鉴权: " + mi.getMethod()));
			}
			throw ae;
		}
	}
}