/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro;

import com.example.demo.bean.User;
import com.example.demo.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 16:47
 */
@Log4j2
@Component
public class AppRealm extends AuthorizingRealm {
	
	Map<String,String> userMap = new HashMap<>(16);
	
	{
		userMap.put("Mark","283538989cef48f3d7d8a1c1bdf2008f");
		super.setName("customRealm");
	}
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean isPermitted(PrincipalCollection principals, String permission) {
		return true;
	}
	
	/**
	 * 该方法主要是用于当前登录用户授权
	 *
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 1.从主体传过来的认证信息中，获得用户名
		String userName = (String) token.getPrincipal();
		// 2.通过用户名到数据库中获得凭证
		String password = getPasswordByUserName(userName);
		if (password == null) {
			return null;
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("Mark", password, "customRealm");
		// 这个是直接加盐在原来加密的基础上
		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("Mark"));
		return authenticationInfo;
	}
	
	/**
	 * 该方法主要是进行用户验证的
	 *
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String) principals.getPrimaryPrincipal();
		Set<String> roles = getRoleByUserName(userName);
		Set<String> permissions = getPermissionByUserName(userName);
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setRoles(roles);
		simpleAuthorizationInfo.setStringPermissions(permissions);
		return simpleAuthorizationInfo;
	}
	
	private String getPasswordByUserName(String userName) {
		return userMap.get(userName);
	}
	
	private Set<String> getPermissionByUserName(String userName) {
		Set<String> sets = new HashSet<>(16);
		sets.add("user:delete");
		sets.add("user:add");
		return sets;
	}
	
	private Set<String> getRoleByUserName(String userName) {
		Set<String> sets = new HashSet<>(10);
		sets.add("admin");
		sets.add("user");
		return sets;
	}
	
}
