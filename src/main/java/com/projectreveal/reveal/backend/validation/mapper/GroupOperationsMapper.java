package com.projectreveal.reveal.backend.validation.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GroupOperationsMapper {
	
	@Select("select count(*) from groups where group_id = #{groupId}")
	public int getGroupCountById(long groupId);
	
	@Select("select count(*) from groups where group_name=#{groupName}")
	public int getGroupCountByName(String groupName);
	
		
}
