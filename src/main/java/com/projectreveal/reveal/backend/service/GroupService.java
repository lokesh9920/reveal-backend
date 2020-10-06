package com.projectreveal.reveal.backend.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectreveal.reveal.backend.mapper.GroupMapper;
import com.projectreveal.reveal.backend.model.GroupMetadata;

@Service
public class GroupService {
	
	@Autowired
	GroupMapper groupMapper;

	public ArrayList<GroupMetadata> getAllGroupMetaData(){
		
		return groupMapper.getAllGroupsMetaData();
		
	}
}
