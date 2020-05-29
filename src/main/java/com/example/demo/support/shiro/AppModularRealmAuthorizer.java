/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro;

import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 17:21
 */
public class AppModularRealmAuthorizer extends ModularRealmAuthorizer {
	
	@Override
	public boolean isPermitted(PrincipalCollection principals, String permission) {
		return super.isPermitted(principals, permission);
	}
	
	@Override
	public void checkPermission(PrincipalCollection principals, Permission permission) {
		super.checkPermission(principals, permission);
	}
	
	@Override
	public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
		return super.hasRole(principals, roleIdentifier);
	}
	
}