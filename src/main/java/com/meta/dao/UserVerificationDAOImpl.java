package com.meta.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meta.dto.User_verificationDTO;

public class UserVerificationDAOImpl implements UserVerificationDAO {

	@Autowired
	@Qualifier(value = "OneSqlSession")
	private SqlSession sqlsession;
	
	public static final String namespace = "mappers.user_verificationMapper";

	@Override
	public void MailCodeSend(User_verificationDTO dto) throws Exception {
		sqlsession.insert(namespace+".MailCodeSend", dto);
	}

	@Override
	public String MailChk(String user_id) throws Exception {
		return sqlsession.selectOne(namespace+".MailChk", user_id);
	}
	
	@Override
	public void UpdateStatus(String verification_code) throws Exception {
		sqlsession.update(namespace+".UpdateStatus", verification_code);	
	}
	
	@Override
	public void DeleteVerifiaction(String user_id) throws Exception {
		sqlsession.delete(namespace+".DeleteVerifiaction", user_id);
	}
}
