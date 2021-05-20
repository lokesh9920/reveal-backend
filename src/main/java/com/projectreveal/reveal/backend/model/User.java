package com.projectreveal.reveal.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor
public class User {

	String firstName;
	String lastName;
	String userName;
	long groupId;
}
