/*
 * Author github: https://github.com/zs-neo
 * Author Email: 2931622851@qq.com
 */
package com.example.demo.mapper;


import com.example.demo.bean.Auth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhousheng
 * @version 1.0
 * @since 2020/5/29 16:29
 */
@Mapper
public interface AuthMapper {
	
	@Select("select * from auth where username=#{name}")
	List<Auth> getUserAuth(@Param("name") String name);
	
}
