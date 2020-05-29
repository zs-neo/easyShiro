/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro.config;

import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * shiro的行为参数控制
 *
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 13:47
 */
@Log4j2
@Data
@Component
public class AppControllerActionAuthConfig {
	
	@Value("${shiro.enabled}")
	private boolean isEnabled;
	
	private boolean view;
	
	private boolean update;
	
	private boolean delete;
	
	private boolean add;
	
}
