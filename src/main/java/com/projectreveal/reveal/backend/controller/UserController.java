package com.projectreveal.reveal.backend.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectreveal.reveal.backend.entity.Group;
import com.projectreveal.reveal.backend.entity.User;
import com.projectreveal.reveal.backend.logging.LoggingConstants;
import com.projectreveal.reveal.backend.model.UserDetails;
import com.projectreveal.reveal.backend.service.UserService;
import com.projectreveal.reveal.backend.validation.UserValidation;

@RestController
@RequestMapping("/users")
public class UserController {

	
	@Autowired
	private UserValidation userValidation;
	
	@Autowired
	private UserService userService;
	
	Logger logger = LogManager.getLogger(UserService.class);
	
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody UserDetails userDetails){
		logger.info(LoggingConstants.REQUEST_LOGGER,"POST","/users");
		
		userValidation.isUserGoodToRegister(userDetails);
		Group thisUserGroup = userService.getExistingOrCreatedGroup(userDetails);
		userDetails.setGroupId(thisUserGroup.getGroupId());
		User user = userService.createUser(userDetails);
		
		return new ResponseEntity<User>(user,HttpStatus.CREATED);
	}
}
