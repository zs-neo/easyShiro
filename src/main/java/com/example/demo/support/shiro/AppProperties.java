/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import sun.nio.ch.ThreadPool;

/**
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 15:25
 */
@Data
@Configuration
public class AppProperties {
	
	private SecurityProperties security = new SecurityProperties();
	
	@Data
	public static class  SecurityProperties{
		private String[] includePathPatterns ={ "/**"};
		private String[] excludePathPatterns = {"/api/exclude/**"};
	}
	
}
