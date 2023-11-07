package com.meta.service;

import java.util.List;

import com.meta.dto.UserDTO;

public interface SecurityService {

	public String CreateToken(List<UserDTO> dto);
	
	public String getSubject(String Token);
	
	public boolean validationToken(String token);
	
	public boolean expireToken(String token);
}
