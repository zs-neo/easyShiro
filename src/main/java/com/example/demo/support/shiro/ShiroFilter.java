/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * mvc过滤器组件，暂时不用
 *
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 22:11
 */
@Log4j2
@Data
@EqualsAndHashCode(callSuper = false)
public class ShiroFilter extends AuthorizationFilter {
	
	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
		log.info("shiro filter check if is access allowed");
		return true;
	}
	
	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		log.info("shiro filter redirect to login");
		super.redirectToLogin(request, response);
	}
	
}