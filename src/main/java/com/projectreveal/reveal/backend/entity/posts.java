package com.projectreveal.reveal.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class posts {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@Column(name = "post_content", columnDefinition = "CLOB", nullable = false)
	String postContent;
	
	@Column(name = "likes_count", columnDefinition = "int default 0")
	int likesCount;
	
	@Column(name = "dislikes_count", columnDefinition = "int default 0")	
	int dislikesCount;
	
	@Column(name = "time_stamp", columnDefinition = "int default 0")
	long timeStamp;
	
	@Column(name = "created_by")
	String createdBy;
}
