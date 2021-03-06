package com.projectreveal.reveal.backend.controller;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.projectreveal.reveal.backend.logging.LoggingConstants;
import com.projectreveal.reveal.backend.model.NewPost;
import com.projectreveal.reveal.backend.model.Post;
import com.projectreveal.reveal.backend.service.PostLoginService;
import com.projectreveal.reveal.backend.validation.ParameterValidation;
import com.projectreveal.reveal.backend.validation.UserValidation;

@RestController
@RequestMapping("/posts")
public class PostController {

	private final Logger logger = LogManager.getLogger(PostController.class);
	
	@Autowired
	private ParameterValidation parameterValidation;
	
	@Autowired
	private UserValidation userValidation;
	@Autowired
	private PostLoginService postLoginService;
	
	@GetMapping
	@CrossOrigin
	public ResponseEntity<Object> getPosts(@RequestParam("groupId") long groupId, 
										   @RequestParam(name = "timeStamp", required = false ) Long timeStamp) 
						throws JsonMappingException, JsonProcessingException{
		
		
		logger.info(LoggingConstants.REQUEST_LOGGER,"GET","/posts");
		
		if(timeStamp == null) timeStamp = System.currentTimeMillis();
		parameterValidation.validateGroup(groupId);
		HashMap<String, Object> postsResult = null;
		postsResult= postLoginService.getallPosts(groupId, timeStamp);
		
		logger.info(LoggingConstants.RESPONSE_BODY,"GET","/posts",HttpStatus.OK,postsResult);
		return new ResponseEntity<Object>(postsResult,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Object> createPost(@RequestBody NewPost newPost){
		logger.info(LoggingConstants.REQUEST_LOGGER,"POST","/posts");

		parameterValidation.validateGroup(newPost.getGroup());
		userValidation.isUserExisting(newPost.getPostCreator());
		
		Post createdPost = postLoginService.createNewPost(newPost);
		
		return new ResponseEntity<Object>(createdPost, HttpStatus.CREATED);
	}
	
}
