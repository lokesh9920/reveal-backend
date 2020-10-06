package com.projectreveal.reveal.backend.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectreveal.reveal.backend.exceptionhandler.RegistrationFailed;
import com.projectreveal.reveal.backend.exceptionhandler.UnprocessableRequest;
import com.projectreveal.reveal.backend.model.UserDetails;
import com.projectreveal.reveal.backend.validation.mapper.UserOperationsMapper;

@Service
public class UserValidation {

	@Autowired
	private UserOperationsMapper userOperationsMapper;
	
	
	public boolean isUserGoodToRegister(UserDetails userDetails){
		isUserNameUnique(userDetails.getUserName());
		isMailIdUnique(userDetails.getMailId());
		return true;
	}
	
	public boolean isUserExisting(String userName) {
		int count = userOperationsMapper.getUserbyUserName(userName);
		if(count != 1) 
			throw new UnprocessableRequest("user not registered yet");
		return true;
		
	}
	
	
	
	private void isUserNameUnique(String userName) {
		int count = userOperationsMapper.getUserbyUserName(userName);
		if(count!=0)
			throw new RegistrationFailed("UserName should be unique");
		
	}
	
	private void isMailIdUnique(String mailId) {
		int count = userOperationsMapper.getUserbyMail(mailId);
		if(count!=0)
			throw new RegistrationFailed("Mail id already registered");
	}
	
	
	
}
