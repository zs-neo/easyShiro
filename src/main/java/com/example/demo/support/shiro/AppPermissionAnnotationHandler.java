/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro;

import com.example.demo.support.annocation.PermissionCheck;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;

/**
 * PermissionCheck注解处理器
 *
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 13:47
 */
@Log4j2
public class AppPermissionAnnotationHandler extends AuthorizingAnnotationHandler {
	
	public AppPermissionAnnotationHandler() {
		super(PermissionCheck.class);
	}
	
	@Override
	public void assertAuthorized(Annotation a) throws AuthorizationException {
		log.info("AuthorizingAnnotationHandler assert if Authorized now");
		if (a instanceof PermissionCheck) {
			PermissionCheck permissionCheck = (PermissionCheck) a;
			String[] values = permissionCheck.value();
			Subject subject = this.getSubject();
			boolean hasAtLeastOnePermission = false;
			for (String str : values) {
				if (subject.isPermitted(str)) {
					hasAtLeastOnePermission = true;
					break;
				}
			}
			if(!hasAtLeastOnePermission){
				throw new AuthorizationException("no permission");
			}
		}
	}
}
