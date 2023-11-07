package com.meta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.dao.UserVerificationDAO;
import com.meta.dto.User_verificationDTO;

@Service
public class UserVerificationServiceImpl implements UserVerificationService {

	@Autowired
	UserVerificationDAO dao;
	
	@Override
	public void MailCodeSend(User_verificationDTO dto) throws Exception {
		dao.MailCodeSend(dto);
	}
	
	@Override
	public String MailChk(String user_id) throws Exception {
		return dao.MailChk(user_id);
	}
	
	@Override
	public void UpdateStatus(String verification_code) throws Exception {
		dao.UpdateStatus(verification_code);
	}
	
	@Override
	public void DeleteVerifiaction(String user_id) throws Exception {
		dao.DeleteVerifiaction(user_id);
	}
}
