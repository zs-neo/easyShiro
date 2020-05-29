/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * support包的全局应用参数配置
 *
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 15:25
 */
@Data
@Component
public class AppProperties {
	
	private SecurityProperties security = new SecurityProperties();
	
	@Data
	public static class SecurityProperties {
		private String[] includePathPatterns = {"/**"};
		private String[] excludePathPatterns = {"/api/login*"};
	}
	
}
