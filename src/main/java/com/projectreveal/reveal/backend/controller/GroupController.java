package com.projectreveal.reveal.backend.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectreveal.reveal.backend.logging.LoggingConstants;
import com.projectreveal.reveal.backend.model.GroupMetadata;
import com.projectreveal.reveal.backend.service.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupController {
	
	@Autowired
	GroupService groupService;
	
	Logger logger = LogManager.getLogger(GroupController.class);
	
	@GetMapping
	@CrossOrigin
	public ResponseEntity<Object> getAllGroups(){
	
		logger.info(LoggingConstants.REQUEST_LOGGER,"GET","/groups");
		
		ArrayList<GroupMetadata> groupsList = new ArrayList<GroupMetadata>();
		groupsList = groupService.getAllGroupMetaData();
		
		HashMap<String, Object> finalMap = new HashMap<String, Object>();
		finalMap.put("groups", groupsList);
		
		logger.info(LoggingConstants.RESPONSE_BODY,"GET","/groups",HttpStatus.OK.toString(),finalMap);
		return new ResponseEntity<Object>(finalMap,HttpStatus.OK);
	}

}
