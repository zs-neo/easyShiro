/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.mapper;

import com.example.demo.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/28 17:39
 */
@Mapper
public interface UserMapper {
	
	@Select("select * from user where username=#{name}")
	User getUsersByName(@Param("name") String name);
	
}
