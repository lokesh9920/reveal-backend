package com.projectreveal.reveal.backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.projectreveal.reveal.backend.entity.User;
import com.projectreveal.reveal.backend.model.UserDetails;

@Mapper
public interface UserMapper {

	@Select("select * from user_details where user_name=#{userName}")
	@Results({
		@Result(property = "firstName",column = "first_name"),
		@Result(property = "lastName",column = "last_name"),
		@Result(property = "userName",column = "user_name"),
		@Result(property = "mailId",column = "mail_id"),
		@Result(property = "groupId",column = "group_id")
	})
	public User getUserByUserName(String userName);
	
	@Insert("insert into user_details (first_name,last_name,mail_id,user_name,password,group_id) values (#{firstName},#{lastName},#{mailId},#{userName},#{password},#{groupId})")
	public void createUser(UserDetails userDetails);
}
