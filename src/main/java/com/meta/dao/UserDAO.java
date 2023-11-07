package com.meta.dao;

import java.util.List;

import com.meta.dto.UserAdminDTO;
import com.meta.dto.UserDTO;
import com.meta.dto.UserSendDTO;

public interface UserDAO {

	public List<UserDTO> searchUser2(UserSendDTO dto) throws Exception;
	
	public List<UserDTO> searchUser(UserSendDTO dto) throws Exception;
	
	public void RegisterAdminUser(UserDTO dto) throws Exception;
	
	public void UserTemptoryUpdate0(UserDTO dto) throws Exception;
	
	public void UserTemptoryUpdate1(UserDTO dto) throws Exception;
	
	public void TemporaryPW(UserDTO dto) throws Exception;
	
	public void RegisterUser(UserDTO dto) throws Exception;

	public Integer OverlapID(String user_id) throws Exception;
	
	public String loginCheck(String user_id) throws Exception;
	
	public void UserRegisterCancel(String user_id) throws Exception;
	
	public List<UserDTO> loginSuccess(String user_id) throws Exception;
	
	public void IdExpiration(String user_id) throws Exception;
	
}
