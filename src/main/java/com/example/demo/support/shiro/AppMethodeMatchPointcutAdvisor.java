/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro;

import com.example.demo.support.annocation.PermissionCheck;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 静态切入点只在代理创建时执行一次，而不是在运行期间每次调用方法时都执行，所以性能比动态切入点要好，是首选的切入点方式
 *
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 14:40
 */
@Log4j2
public class AppMethodeMatchPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor {
	
	private SecurityManager securityManager = null;
	
	private static final Class<? extends Annotation>[] ANNOTATION_CLASSES =
			new Class[]{
					RequiresPermissions.class, RequiresRoles.class,
					RequiresUser.class, RequiresGuest.class, RequiresAuthentication.class,
					PermissionCheck.class
			};
	
	public AppMethodeMatchPointcutAdvisor() {
		setAdvice(new AppPermissionAopAllianceAnnotationsAuthorizingMethodInterceptor());
	}
	
	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		Method methodTemp = method;
		if (isAuthzAnnotationPresent(methodTemp)) {
			return true;
		}
		if (targetClass != null) {
			try {
				methodTemp = targetClass.getMethod(methodTemp.getName(), method.getParameterTypes());
				if (isAuthzAnnotationPresent(methodTemp)) {
					log.info("Static Method Match success");
					return true;
				}
			} catch (NoSuchMethodException e) {
				log.warn("Static Method Match error: {}", e);
			}
		}
		return false;
	}
	
	private boolean isAuthzAnnotationPresent(Method method) {
		for (Class<? extends Annotation> annotationClass : ANNOTATION_CLASSES) {
			if (AnnotationUtils.findAnnotation(method, annotationClass) != null) {
				return true;
			}
		}
		return false;
	}
	
	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}
}
