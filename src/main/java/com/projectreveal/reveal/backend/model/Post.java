package com.projectreveal.reveal.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Post {

	
	public long id;
	public String postContent;
	public long likesCount;
	public long dislikesCount;
	public long timeStamp;
	
	
}
