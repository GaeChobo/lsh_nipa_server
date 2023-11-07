package com.meta.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meta.dto.MarkerDTO;

public class MarkerDAOImpl implements MarkerDAO {

	@Autowired
	@Qualifier(value = "OneSqlSession")
	private SqlSession sqlsession;
	
	public static final String namespace = "mappers.markerMapper";
	
	@Override
	public void MarkerImgDelete(MarkerDTO dto) throws Exception {
		sqlsession.update(namespace+".MarkerImgDelete", dto);
	}
	
	@Override
	public void MarkerDelete(MarkerDTO dto) throws Exception {
		sqlsession.delete(namespace+".MarkerDelete", dto);
	}
	
	@Override
	public void MarkerUpdate(MarkerDTO dto) throws Exception {
		sqlsession.update(namespace+".MarkerUpdate", dto);
	}
	
	@Override
	public List<MarkerDTO> AdminMarkerList(MarkerDTO dto) throws Exception {
		return sqlsession.selectList(namespace+"AdminMarkerList", dto);		
	}
	
	@Override
	public List<MarkerDTO> AdminMarkerSearch(MarkerDTO dto) throws Exception {
		return sqlsession.selectList(namespace+"AdminMarkerSearch", dto);	
	}
	
	@Override
	public void MarkerRegister(MarkerDTO dto) throws Exception {
		sqlsession.insert(namespace+".MarkerRegister", dto);
	}
	
	@Override
	public List<MarkerDTO> MarkerFindValue(String marker_no) throws Exception {
		return sqlsession.selectList(namespace+".MarkerFindValue", marker_no);
	}
	
}
