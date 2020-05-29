/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.controller;

import com.example.demo.bean.User;
import com.example.demo.service.UserService;
import com.example.demo.support.annocation.PermissionCheck;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class DemoController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/hello")
	@PermissionCheck("hello:view")
	public String hello() {
		return "hello";
	}
	
	@GetMapping("/hi")
	@PermissionCheck("hi:view")
	public String hi() {
		return "hi";
	}
	
	@GetMapping("/exclude")
	public String exclude() {
		return "exclude";
	}
	
	@GetMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
		subject.login(usernamePasswordToken);
		return "suc";
	}
	
	@GetMapping("/user")
	@PermissionCheck("user:view")
	public Object get(@RequestParam String name) {
		return userService.getUsersByName(name);
	}
	
}