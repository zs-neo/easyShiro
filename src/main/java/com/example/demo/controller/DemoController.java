/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.support.annocation.PermissionCheck;
import com.example.demo.support.beans.CodeMsg;
import com.example.demo.support.exception.GlobalException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/hello")
	@PermissionCheck("user:hello")
	public String hello() {
		return "hello";
	}
	
	@GetMapping("/hi")
	@PermissionCheck("user:hi,demo:good,demo:test")
	public String hi() {
		return "hi";
	}
	
	@GetMapping("/ok")
	@PermissionCheck("user:ok")
	public String ok() {
		return "ok";
	}
	
	@GetMapping("/exclude")
	public String exclude() {
		return "exclude";
	}
	
	@GetMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password) {
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.isAuthenticated()) {
				token.setRememberMe(true);
				currentUser.login(token);
			}
			return "suc";
		} catch (UnknownAccountException uae) {
			throw new GlobalException(CodeMsg.UNKONWN_ACCOUNT_ERROR);
		} catch (LockedAccountException lae) {
			throw new GlobalException(CodeMsg.LOCKED_ACCOUNT_ERROR);
		} catch (ExcessiveAttemptsException eae) {
			throw new GlobalException(CodeMsg.ATTEMPT_ACCOUNT_ERROR);
		} catch (AuthenticationException ae) {
			throw new GlobalException(CodeMsg.ACCOUNT_ERROR);
		} catch (Exception e) {
			throw new GlobalException(CodeMsg.ACCOUNT_ERROR);
		}
	}
	
	@RequestMapping(value = "/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "login";
	}
	
	@GetMapping("/user")
	@PermissionCheck("user:view")
	public Object get(@RequestParam String name) {
		return userService.getUsersByName(name);
	}
	
}