/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro.annotationsupport;

import com.example.demo.support.annocation.PermissionCheck;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 1.考慮使用StaticMethodMatcherPointcutAdvisor
 * 2.静态切入点只在代理创建时执行一次，而不是在运行期间每次调用方法时都执行，所以性能比动态切入点要好，是首选的切入点方式
 * 3.暂时用
 *
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 14:40
 */
@Log4j2
public class AppMethodeMatchPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor {
	
	protected SecurityManager securityManager = null;
	
	private static final Class<? extends Annotation>[] ANNOTATION_CLASSES =
			new Class[]{
					RequiresPermissions.class, RequiresRoles.class,
					RequiresUser.class, RequiresGuest.class, RequiresAuthentication.class,
					PermissionCheck.class
			};
	
	public AppMethodeMatchPointcutAdvisor() {
		setAdvice(new AppPermissionAopAllianceAnnotationsAuthorizingMethodInterceptor());
		log.info("set advisor AppPermissionAopAllianceAnnotationsAuthorizingMethodInterceptor");
	}
	
	
	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}
	
	@Override
	public boolean matches(Method method, Class targetClass) {
		Method m = method;
		if (isAuthzAnnotationPresent(m)) {
			return true;
		}
		if (targetClass != null) {
			try {
				m = targetClass.getMethod(m.getName(), m.getParameterTypes());
				if (isAuthzAnnotationPresent(m)) {
					return true;
				}
			} catch (NoSuchMethodException e) {
				//忽略异常，如果不是授权注解就忽略
			}
		}
		return false;
	}
	
	
	private boolean isAuthzAnnotationPresent(Method method) {
		for (Class<? extends Annotation> annClass : ANNOTATION_CLASSES) {
			Annotation a = AnnotationUtils.findAnnotation(method, annClass);
			if (a != null) {
				return true;
			}
		}
		return false;
	}
	
	
}