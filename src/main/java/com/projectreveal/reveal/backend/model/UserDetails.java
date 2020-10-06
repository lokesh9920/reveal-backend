package com.projectreveal.reveal.backend.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserDetails {

	@Null
	private long id;
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@Email
	private String mailId;
	@NotEmpty
	private String userName;
	@NotEmpty
	private String password;
	@NotEmpty
	private String groupName;
	
	@Null
	private long groupId;
}
