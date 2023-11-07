package com.meta.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meta.dto.CctvDTO;

public class CctvDAOImpl implements CctvDAO {

	@Autowired
	@Qualifier(value = "OneSqlSession")
	private SqlSession sqlsession;
	
	public static final String namespace = "mappers.cctvMapper";
	
	@Override
	public void CCTVAllDelete() throws Exception {
		sqlsession.delete(namespace+".CCTVAllDelete");
	}
	
	@Override
	public List<CctvDTO> CCTVAllList() throws Exception {
		return sqlsession.selectList(namespace+".CCTVAllList");
	}
	
	@Override
	public List<CctvDTO> AdminCctvSearch(CctvDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".AdminCctvSearch", dto);
	}
	
	@Override
	public List<CctvDTO> CCTV_NO1_Find(CctvDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".CCTV_NO1_Find", dto);
	}

	@Override
	public List<CctvDTO> CCTV_NO2_Find(CctvDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".CCTV_NO2_Find", dto);
	}
	
	@Override
	public List<CctvDTO> CCTV_NO3_Find(CctvDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".CCTV_NO3_Find" , dto);
	}
	
	@Override
	public List<CctvDTO> CCTV_NO4_Find(CctvDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".CCTV_NO4_Find", dto);
	}
	
	@Override
	public void RegisterCCTV(CctvDTO dto) throws Exception {
		sqlsession.insert(namespace+".RegisterCCTV", dto);
	}
	
	@Override
	public String CCTVImageFindList(String cctv_no) throws Exception {
		String resultlist = sqlsession.selectOne(cctv_no);
		return resultlist;
	}
}
