/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.support.shiro.authdata;

import lombok.Data;

import java.util.List;

/**
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 16:23
 */
@Data
public class AppAuthManager {
	
	private List<AppOperation> operations;
	private List<AppResource> resources;
	private List<AppRole> roles;
	
}
