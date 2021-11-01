package com.projectreveal.reveal.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class NewPost {

	private String postCreator;
	private int groupId;
	private String PostContent;
}
