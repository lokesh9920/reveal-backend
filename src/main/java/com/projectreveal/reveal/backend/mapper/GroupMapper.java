package com.projectreveal.reveal.backend.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.projectreveal.reveal.backend.model.GroupMetadata;

@Mapper
public interface GroupMapper {
	
	@Select("select group_id,group_name from groups order by group_name")
	@Results({
		@Result(property = "groupId",column = "group_id"),
		@Result(property = "groupName",column = "group_name")
	})
	public ArrayList<GroupMetadata> getAllGroupsMetaData();
}
