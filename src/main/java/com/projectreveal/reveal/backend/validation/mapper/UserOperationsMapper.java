package com.projectreveal.reveal.backend.validation.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserOperationsMapper {
	
	@Select("select count(*) from user_details where user_name=#{userName}")
	public int getUserbyUserName(String userName);
	
	@Select("select count(*) from user_details where mail_id=#{mailId}")
	public int getUserbyMail(String mailId);
}
