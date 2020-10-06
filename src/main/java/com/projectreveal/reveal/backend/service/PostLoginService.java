package com.projectreveal.reveal.backend.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectreveal.reveal.backend.logging.LoggingConstants;
import com.projectreveal.reveal.backend.model.NewPost;
import com.projectreveal.reveal.backend.model.Post;
import com.projectreveal.reveal.backend.model.PostsWrapper;

@Service
public class PostLoginService {
	
	@Value("${reveal.postService.url}")
	private String postServiceUrl;
	
	@Value("${reveal.postService.fetchCount}")
	private int postFetchCount;
	
	Logger logger = LogManager.getLogger(PostLoginService.class);
	ObjectMapper objectMapper = new ObjectMapper();
	
	
	public HashMap<String, Object> getallPosts(long groupId, Long timeStamp) throws JsonMappingException, JsonProcessingException{
		
		HashMap<String, Object> finalResult = new HashMap<String, Object>();
		RestTemplate restTemplate = new RestTemplate();		
		
		logger.info(LoggingConstants.REST_REQUEST_ATTEMPT,postServiceUrl,"Fetch Posts");
		ResponseEntity<PostsWrapper> fetchedPosts = 
		//restTemplate.getForEntity(postServiceUrl + "/posts?groupId="+groupId+"&timStamp="+timeStamp+"&count="+postFetchCount,PostsWrapper.class);
		restTemplate.getForEntity(postServiceUrl + "/posts", PostsWrapper.class);
		logger.info(LoggingConstants.REST_REQUEST_STATUS_REPORT, postServiceUrl,fetchedPosts.getStatusCode().toString());
		
		ArrayList<Post> fetchedPostList = fetchedPosts.getBody().getPosts();
		finalResult.put("length", fetchedPostList.size());
		if(!fetchedPostList.isEmpty()) {
			finalResult.put("posts", fetchedPostList);
		}
		return finalResult;
		
	}
	
	public Post createNewPost(NewPost newPost) {
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Post> postboxResponse = restTemplate.postForEntity(postServiceUrl+"/posts", newPost, Post.class);
		
		Post createdPost = postboxResponse.getBody();
		
		return createdPost;
	}

}