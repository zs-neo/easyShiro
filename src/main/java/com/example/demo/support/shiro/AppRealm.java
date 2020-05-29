/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro;

import com.example.demo.bean.Auth;
import com.example.demo.bean.User;
import com.example.demo.mapper.AuthMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.support.beans.CodeMsg;
import com.example.demo.support.exception.GlobalException;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 16:47
 */
@Log4j2
public class AppRealm extends AuthorizingRealm {
	
	@Autowired
	private AuthMapper authMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public boolean isPermitted(PrincipalCollection principals, String permission) {
		List<String> users = principals.asList();
		if (users == null) {
			throw new GlobalException(CodeMsg.NO_PERMISSIONS);
		}
		String username = users.get(0);
		List<Auth> auths = authMapper.getUserAuth(username);
		String[] permissions = permission.split(",");
		for (Auth auth : auths) {
			for (String permissionStr : permissions) {
				//任何一个匹配到
				if (auth.getPermission().equals(permissionStr)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 该方法主要是用于当前登录用户授权
	 *
	 * @param authcToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		log.info("doGetAuthorizationInfo do user authorize");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = token.getUsername();
		String password = String.valueOf(token.getPassword());
		User user = userMapper.getUsersByName(username);
		if (user == null) {
			throw new GlobalException(CodeMsg.NO_PERMISSIONS);
		}
		if (password.equals(user.getPassword())) {
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName());
			return info;
		} else {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
	}
	
	/**
	 * 该方法主要是进行用户验证的,没找出问题来,调用不了
	 *
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.info("doGetAuthorizationInfo do user check");
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setRoles(null);
		simpleAuthorizationInfo.setStringPermissions(null);
		return simpleAuthorizationInfo;
	}
	
}
