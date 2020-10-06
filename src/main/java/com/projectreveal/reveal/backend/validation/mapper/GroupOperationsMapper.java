package com.projectreveal.reveal.backend.validation.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.projectreveal.reveal.backend.entity.Group;

@Mapper
public interface GroupOperationsMapper {
	
	@Select("select count(*) from groups where group_id = #{groupId}")
	public int getGroupCountById(long groupId);
	
	@Select("select count(*) from groups where group_name=#{groupName}")
	public int getGroupCountByName(String groupName);
	
		
}
