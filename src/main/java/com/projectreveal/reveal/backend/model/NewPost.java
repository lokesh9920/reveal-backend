package com.projectreveal.reveal.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class NewPost {

	private String postCreator;
	private String group;
	private String PostContent;
}
