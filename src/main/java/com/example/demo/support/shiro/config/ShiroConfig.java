/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro.config;

import com.example.demo.support.filter.GlobalFilter;
import com.example.demo.support.shiro.AppRealm;
import com.example.demo.support.shiro.annotationsupport.AppMethodeMatchPointcutAdvisor;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 16:03
 */
@Log4j2
@Configuration
public class ShiroConfig {
	
	@Value("${shiro.enabled}")
	private boolean isEnabled;
	
	@Value("${shiro.rememberMe.cookie.name}")
	private String cookieName;
	
	@Value("${shiro.rememberMe.cookie.maxAge}")
	private int maxAge;
	
	
	@Bean
	public FilterRegistrationBean someFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(globalFilter());
		registration.addUrlPatterns("/*");
		registration.setName("globalFilter");
		registration.setOrder(1);
		return registration;
	}
	
	/**
	 * 创建一个bean
	 *
	 * @return
	 */
	@Bean(name = "globalFilter")
	public Filter globalFilter() {
		return new GlobalFilter();
	}
	
	
	@Bean
	public SecurityManager securityManager(RememberMeManager rememberMeManager,
										   @Qualifier("appRealm") AppRealm appRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRememberMeManager(rememberMeManager);
		securityManager.setRealm(appRealm);
		return securityManager;
	}
	
	@Bean
	public AppRealm appRealm() {
		return new AppRealm();
	}
	
	/**
	 * 授权流程
	 * 1.对 subject 进行授权, 调用方法 isPermitted("")
	 * 2.SecurityManager 执行授权, 通过 ModularRealmAuthorizer 执行授权.
	 * 3.ModularRealmAuthorizer 调用 Realm(我们自定义的)的 doGetAuthorizationInfo 方法 从数据库查询权限数据, 并返回给ModularRealmAuthorizer.
	 * 4.ModularRealmAuthorizer 调用 PermissionResolver 进行权限对比.
	 * 5.对比后,如果 isPermitted 方法中的参数在 realm 查询到的权限数据中,说明用户有权限,否则没有权限.
	 *
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		log.info("shiro filter init chain");
		// shiro过滤器工厂类
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		Map<String, Filter> filterMap = new HashMap<>();
//		filterMap.put("shiroFilter", new ShiroFilter());
		shiroFilterFactoryBean.setFilters(filterMap);
		/*
		 *   Shiro内置过滤器，可以实现权限相关的拦截器，常用的有：
		 *   anon：无需认证（登录）即可访问
		 *   authc：必须认证才可以访问
		 *   user：如果使用rememberme的功能可以直接访问
		 *   perms：该资源必须得到资源权限才能访问
		 *   role：该资源必须得到角色资源才能访问
		 */
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/api/login", "anon");
		filterChainDefinitionMap.put("/**", "authc");
		// 如果不设置默认会自动寻找Web工程根目录下的"/login"页面
		shiroFilterFactoryBean.setLoginUrl("/login.html");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index.html");
		//未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized.html");
		// 将拦截器链设置到shiro中
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
		
	}
	
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator bean = new DefaultAdvisorAutoProxyCreator();
		bean.setProxyTargetClass(true);
		return bean;
	}
	
	/**
	 * 开启注解式 权限拦截
	 *
	 * @return
	 */
	@Bean
	public AppMethodeMatchPointcutAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
		log.info("init bean advisor");
		AppMethodeMatchPointcutAdvisor bean = new AppMethodeMatchPointcutAdvisor();
		bean.setSecurityManager(securityManager);
		return bean;
	}
	
	@Bean
	public CookieRememberMeManager rememberMeManager(SimpleCookie simpleCookie) {
		CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
		rememberMeManager.setCookie(simpleCookie);
		return rememberMeManager;
	}
	
	@Bean
	public SimpleCookie simpleCookie() {
		SimpleCookie cookie = new SimpleCookie(cookieName);
		cookie.setMaxAge(maxAge);
		return cookie;
	}
	
}
