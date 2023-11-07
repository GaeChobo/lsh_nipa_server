package com.meta.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.meta.dto.UserAdminDTO;
import com.meta.dto.UserDTO;
import com.meta.dto.User_verificationDTO;
import com.meta.vo.Message;

public interface UserService {

	public ResponseEntity<Message> UpdatePW(UserDTO dto) throws Exception;
	
	public ResponseEntity<Message> TemporaryPW(UserDTO dto) throws Exception;
	
	public ResponseEntity<Message> RegisterUser(UserDTO dto) throws Exception;

	public ResponseEntity<Message> MailChk(User_verificationDTO dto) throws Exception;

	public ResponseEntity<Message> MailCodeSend(User_verificationDTO dto) throws Exception;	
	
	public Integer OverlapID(String user_id) throws Exception;
	
	public String loginCheck(String user_id) throws Exception;
	
	public ResponseEntity<Message> UserRegisterCancel(String user_id) throws Exception;
	
	public List<UserDTO> loginSuccess(String user_id) throws Exception;
	
	public void IdExpiration(String user_id) throws Exception;
	
	public ResponseEntity<Message> UserLogin(UserDTO dto) throws Exception;
}
