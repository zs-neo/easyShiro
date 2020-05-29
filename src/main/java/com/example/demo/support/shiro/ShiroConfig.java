/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro;

import lombok.extern.log4j.Log4j2;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	
	/**
	 * 配置安全管理器，并且注入Realm域
	 *
	 * @param realm
	 * @return
	 */
	@Bean
	public SecurityManager securityManager(Realm realm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm);
		return securityManager;
	}
	
	/**
	 * aop支持
	 *
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AppMethodeMatchPointcutAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AppMethodeMatchPointcutAdvisor authorizationAttributeSourceAdvisor = new AppMethodeMatchPointcutAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
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
		filterMap.put("shiroFilter", myShiroFilter());
		shiroFilterFactoryBean.setFilters(filterMap);
		//拦截器----Map集合,配置过滤器,其中的具体的退出代码Shiro已经替我们实现了
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/api/login*", "anon");
		filterChainDefinitionMap.put("/api/user*", "anon");
		filterChainDefinitionMap.put("/**", "authc");
		
		// 如果不设置默认会自动寻找Web工程根目录下的"/login"页面
		shiroFilterFactoryBean.setLoginUrl("/login.html");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index.html");
		//未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		
		// 将拦截器链设置到shiro中
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login.html");
		
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index.html");
		
		//未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		return shiroFilterFactoryBean;
	}
	
	@Bean
	public ShiroFilter myShiroFilter() {
		ShiroFilter shiroFilter = new ShiroFilter();
		return shiroFilter;
	}
	
	@Bean
	public AppRealm realm() {
		AppRealm bosRealm = new AppRealm();
		return bosRealm;
	}
	
}
