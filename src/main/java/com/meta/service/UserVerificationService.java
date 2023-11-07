package com.meta.service;

import com.meta.dto.User_verificationDTO;

public interface UserVerificationService {

	public void MailCodeSend(User_verificationDTO dto) throws Exception;
	
	public String MailChk(String user_id) throws Exception;
	
	public void UpdateStatus(String verification_code) throws Exception;
	
	public void DeleteVerifiaction(String user_id) throws Exception;
}
