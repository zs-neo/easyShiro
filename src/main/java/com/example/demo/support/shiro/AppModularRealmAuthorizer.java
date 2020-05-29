/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro;

import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 多realm数据源可以使用该类注册到securitymanager中，暂时不用
 *
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 17:21
 */
@Log4j2
public class AppModularRealmAuthorizer extends ModularRealmAuthorizer {
	
	@Override
	public boolean isPermitted(PrincipalCollection principals, String permission) {
		log.info("invoke isPermitted");
		return super.isPermitted(principals, permission);
	}
	
	@Override
	public void checkPermission(PrincipalCollection principals, Permission permission) {
		log.info("invoke check permission");
		super.checkPermission(principals, permission);
	}
	
	@Override
	public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
		log.info("invoke hasRole");
		return super.hasRole(principals, roleIdentifier);
	}
	
}