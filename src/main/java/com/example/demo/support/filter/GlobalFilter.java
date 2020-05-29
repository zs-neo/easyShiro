/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.filter;

import com.example.demo.support.wrapper.RequestWraper;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 13:47
 */
@Log4j2
@WebFilter(filterName = "GlobalFilter", urlPatterns = "/api")
public class GlobalFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.warn("GlobalFilter init , config: " + filterConfig.toString());
	}
	
	@Override
	public void destroy() {
		log.warn("GlobalFilter destroy");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		String method = servletRequest.getMethod();
		String requestURI = servletRequest.getRequestURI();
		log.info("OpenConsoleFilter doFilter begin! Method = {}, URI = {}", method, requestURI);
		//do nothing now
		filterChain.doFilter(new RequestWraper(servletRequest), response);
		log.info("OpenConsoleFilter doFilter end! Method = {}, URI = {}", method, requestURI);
	}
}
