/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 1.GlobalFilter->preHandle->postHandle->afterCompletion
 * 2.其中注册的exclude路径，如果匹配到是不走这个过滤器的
 * 3.也可以在这里写权限判断的逻辑，但是现在暂时保留
 *
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 15:31
 */
@Log4j2
@Configuration
public class AppMvcInterceptorConfiguration extends HandlerInterceptorAdapter implements WebMvcConfigurer {
	
	@Autowired
	private AppProperties appProperties;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.info("添加过滤路径和非过滤路径");
		registry.addInterceptor(this)
				.addPathPatterns(appProperties.getSecurity().getIncludePathPatterns());
//				.excludePathPatterns(appProperties.getSecurity().getExcludePathPatterns());
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("preHandle:请求前调用");
		String url = request.getRequestURI();
		log.info("请求url:{}", url);
		//返回 false 则请求中断
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
		log.info("postHandle:请求后调用");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.info("afterCompletion:请求调用完成后回调方法，即在视图渲染完成后回调");
	}
	
}
