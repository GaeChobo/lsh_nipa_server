package com.meta.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meta.dto.UserAdminDTO;
import com.meta.dto.UserDTO;
import com.meta.dto.UserSendDTO;

public class UserDAOImpl implements UserDAO {

	@Autowired
	@Qualifier(value = "OneSqlSession")
	private SqlSession sqlsession;
	
	public static final String namespace = "mappers.userMapper";

	@Override
	public List<UserDTO> searchUser2(UserSendDTO dto) throws Exception {
		return sqlsession.selectOne(namespace+".searchUser2", dto);
	}
	
	@Override
	public List<UserDTO> searchUser(UserSendDTO dto) throws Exception {
		return sqlsession.selectOne(namespace+".searchUser", dto);
	}
	
	@Override
	public void RegisterAdminUser(UserDTO dto) throws Exception {
		sqlsession.insert(namespace+".RegisterAdminUser", dto);
	}
	
	@Override
	public void UserTemptoryUpdate0(UserDTO dto) throws Exception {
		sqlsession.update(namespace+".UserTemptoryUpdate0", dto);
	}
	
	@Override
	public void UserTemptoryUpdate1(UserDTO dto) throws Exception{
		sqlsession.update(namespace+".UserTemptoryUpdate1", dto);
	}
	
	@Override
	public void TemporaryPW(UserDTO dto) throws Exception {
		sqlsession.update(namespace+".TemporaryPW", dto);
	}
	
	@Override
	public void RegisterUser(UserDTO dto) throws Exception {
		sqlsession.insert(namespace+".RegisterUser", dto);
	}
	
	@Override
	public Integer OverlapID(String user_id) throws Exception {
		return sqlsession.selectOne(namespace+".OverlapID", user_id);
	}
	
	@Override
	public String loginCheck(String user_id) throws Exception {
		return sqlsession.selectOne(namespace+".loginCheck", user_id);
	}
	
	@Override
	public void UserRegisterCancel(String user_id) throws Exception {
		sqlsession.delete(namespace+".UserRegisterCancel", user_id);
	}
	
	@Override
	public List<UserDTO> loginSuccess(String user_id) throws Exception {
		return sqlsession.selectList(namespace+".loginSuccess", user_id);
	}
	
	@Override
	public void IdExpiration(String user_id) throws Exception {
		sqlsession.update(namespace+".IdExpiration", user_id);
	}
}
