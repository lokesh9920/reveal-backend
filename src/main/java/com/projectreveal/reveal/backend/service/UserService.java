package com.projectreveal.reveal.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectreveal.reveal.backend.entity.Group;
import com.projectreveal.reveal.backend.entity.User;
import com.projectreveal.reveal.backend.mapper.GroupMapper;
import com.projectreveal.reveal.backend.mapper.UserMapper;
import com.projectreveal.reveal.backend.model.UserDetails;

@Service
public class UserService {

	@Autowired
	GroupMapper groupMapper;
	
	@Autowired
	UserMapper userMapper;
	
	public Group getExistingOrCreatedGroup(UserDetails userDetails) {
		Group existingGroup = getExistingGroup(userDetails.getGroupName());
		if(existingGroup != null) return existingGroup;
		
		Group newGroup = new Group();
		newGroup.setGroupCreator(userDetails.getUserName());
		newGroup.setGroupName(userDetails.getGroupName());
		createGroup(newGroup);
		return getExistingGroup(userDetails.getGroupName());
	
	}
	
	public User createUser(UserDetails userDetails) {
		userMapper.createUser(userDetails);
		return userMapper.getUserByUserName(userDetails.getUserName());
		
	}
	
	
	private Group getExistingGroup(String groupName){
		return groupMapper.getGroupByName(groupName);
	}
	
	private void createGroup(Group newGroup) {
		groupMapper.createGroup(newGroup);
	}
}
