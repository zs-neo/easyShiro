/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 22:11
 */
@Configuration
public class ShiroFilter extends AuthorizationFilter {
	
	
	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
		return false;
	}
	
	
}
