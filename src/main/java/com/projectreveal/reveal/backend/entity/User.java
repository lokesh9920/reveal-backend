package com.projectreveal.reveal.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_details")
@Getter @Setter @NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "first_name",nullable = false)
	private String firstName;
	@Column(name = "last_name",nullable = true)
	private String lastName;
	
	@Column(name = "mail_id",nullable = false,unique = true)
	@Email
	private String mailId;
	@Column(name = "user_name",nullable = false,unique = true)
	private String userName;
	
	@Column(name = "password",nullable = false)
	private String password;
	@Column(name = "group_id",nullable = false,unique = false)	
	private long groupId;

}
