/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.service;

import com.example.demo.bean.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 17:39
 */
@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public User getUsersByName(String name){
		return userMapper.getUsersByName(name);
	}

}
