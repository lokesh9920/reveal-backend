package com.projectreveal.reveal.backend.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.projectreveal.reveal.backend.logging.LoggingConstants;
import com.projectreveal.reveal.backend.model.NewPost;
import com.projectreveal.reveal.backend.model.Post;
import com.projectreveal.reveal.backend.service.PostLoginService;
import com.projectreveal.reveal.backend.validation.UserValidation;


/**
 * The below APIs are subject to cookie validation from the filter
 * @author ltangell
 *
 */
@RestController
@RequestMapping("/posts")
//@CrossOrigin(allowCredentials = "true")
//the above allowCredentials is mandatory because frontend consumes these APIs with credentials=include(in order to send cookie by default)
public class PostController {

	private final Logger logger = LogManager.getLogger(PostController.class);
	
	
	@Autowired
	private UserValidation userValidation;
	@Autowired
	private PostLoginService postLoginService;
	
	@GetMapping
	@CrossOrigin
	public ResponseEntity<Object> getPosts(@RequestParam("groupId") long groupId, 
										   @RequestParam(name = "timeStamp", required = false ) Long timeStamp,
										   HttpServletRequest httpRequest) 
						throws JsonMappingException, JsonProcessingException{
		
		
		logger.info(LoggingConstants.REQUEST_LOGGER,"GET","/posts");
		
		if(timeStamp == null) timeStamp = System.currentTimeMillis();
		//The validation is handled by filter through token

		HashMap<String, Object> postsResult = null;
		
		//will use the groupId in token rathern than the query parameter value
		long groupIdFromToken = Long.parseLong(httpRequest.getAttribute("groupId").toString());
		
		postsResult= postLoginService.getallPosts(groupIdFromToken, timeStamp);	
		
		logger.info(LoggingConstants.RESPONSE_BODY,"GET","/posts",HttpStatus.OK,postsResult);
		return new ResponseEntity<Object>(postsResult,HttpStatus.OK);
	}
	
	@PostMapping
	@CrossOrigin
	public ResponseEntity<Object> createPost(@RequestBody NewPost newPost, HttpServletRequest httpRequest){
		logger.info(LoggingConstants.REQUEST_LOGGER,"POST","/posts");
		
		//The validation is handled by filter through token
		
//		parameterValidation.validateGroup(newPost.getGroupId());
//		userValidation.isUserExisting(newPost.getPostCreator());
		
		String userName = httpRequest.getAttribute("userName").toString();
		int groupId = Integer.parseInt(httpRequest.getAttribute("groupId").toString());
		
		//frontend will send dummy values in requestbody, but the actual names are in token
		newPost.setPostCreator(userName);
		newPost.setGroupId(groupId);
		
		Post createdPost = postLoginService.createNewPost(newPost);
		
		return new ResponseEntity<Object>(createdPost, HttpStatus.CREATED);
	}
	
}
