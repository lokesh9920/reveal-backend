package com.projectreveal.reveal.backend.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectreveal.reveal.backend.enums.ValidationEnum;
import com.projectreveal.reveal.backend.exceptionhandler.ResourceNotFound;
import com.projectreveal.reveal.backend.exceptionhandler.UnprocessableRequest;
import com.projectreveal.reveal.backend.validation.mapper.GroupOperationsMapper;

@Service
public class ParameterValidation {
	
	@Autowired
	private GroupOperationsMapper groupOperationsMapper;
	
	public ValidationEnum validateGroup(long groupId) {
		
		ValidationEnum status;
		int groupCount = groupOperationsMapper.getGroupCountById(groupId);
		status = groupCount == 1 ?  ValidationEnum.SUCCESS :  ValidationEnum.FAIL;
		
		if(status.equals(ValidationEnum.SUCCESS))return status;
		throw new ResourceNotFound("Unknown Group");
		
	}
	
	public ValidationEnum validateGroup(String groupName) {
		
		ValidationEnum status;
		int groupCount = groupOperationsMapper.getGroupCountByName(groupName);
		status = groupCount == 1 ?  ValidationEnum.SUCCESS :  ValidationEnum.FAIL;
		
		if(status.equals(ValidationEnum.SUCCESS))return status;
		throw new UnprocessableRequest("Unknown Group");
		
	}
}
