package com.projectreveal.reveal.backend.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectreveal.reveal.backend.exceptionhandler.RestTemplateException;
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
		RestTemplate restTemplate = getRestTemplate();		
		
		logger.info(LoggingConstants.REST_REQUEST_ATTEMPT,postServiceUrl,"Fetch Posts");
		ResponseEntity<PostsWrapper> fetchedPosts = 
				restTemplate.getForEntity(postServiceUrl + "/posts?groupId="+groupId+"&timeStamp="+timeStamp+"&count="+postFetchCount,PostsWrapper.class);
		//restTemplate.getForEntity(postServiceUrl + "/posts", PostsWrapper.class);
		logger.info(LoggingConstants.REST_REQUEST_STATUS_REPORT, postServiceUrl,fetchedPosts.getStatusCode().toString());
		
		ArrayList<Post> fetchedPostList = fetchedPosts.getBody().getPosts();
		finalResult.put("length", fetchedPostList.size());
		if(!fetchedPostList.isEmpty()) {
			finalResult.put("posts", fetchedPostList);
		}
		return finalResult;
		
	}
	
	public Post createNewPost(NewPost newPost) {
		
		RestTemplate restTemplate = getRestTemplate();
		ResponseEntity<Post> postboxResponse = restTemplate.postForEntity(postServiceUrl+"/posts", newPost, Post.class);
		
		Post createdPost = postboxResponse.getBody();
		
		return createdPost;
	}
	
	private RestTemplate getRestTemplate() {
		
		/**
		 * 
		 * Below lines are to skip certificate verfication for consuming https url
		 * 
		 */
		try {
			
		
		TrustStrategy acceptingTrustStrategy = (X509Certificates,s)->true;
	    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
	    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

		
		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(csf)
				.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
	
		// till here
		
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;
		}catch (Exception e) {
			throw new RestTemplateException("Failed to create new RestTemplate instance");
		}
	}

}