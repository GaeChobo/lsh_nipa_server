package com.meta.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meta.dto.InterferenceDTO;
import com.meta.dto.InterferenceLocationDTO;
import com.meta.dto.Interference_detailDTO;
import com.meta.dto.Interference_final_drawingDTO;


public class InterferenceDAOImpl implements InterferenceDAO {

	@Autowired
	@Qualifier(value = "OneSqlSession")
	private SqlSession sqlsession;
	
	public static final String namespace = "mappers.interferenceMapper";
	
	@Override
	public void DeleteFinalInterference(Interference_final_drawingDTO dto) throws Exception {
		sqlsession.delete(namespace+".DeleteFinalInterference", dto);
	}
	
	@Override
	public void RegisterFinalInterference(Interference_final_drawingDTO dto) throws Exception {
		sqlsession.insert(namespace+".RegisterFinalInterference", dto);
	}
	
	@Override
	public void RegisterInterferenceDetail(InterferenceDTO dto) throws Exception {
		sqlsession.insert(namespace+".RegisterInterferenceDetail", dto);
	}
	
	@Override
	public void UpdateInterferenceDetail(Interference_detailDTO dto) throws Exception {
		sqlsession.update(namespace+".UpdateInterferenceDetail", dto);
	}
	
	@Override
	public void RegisterInterferenceLocation(InterferenceLocationDTO dto) throws Exception {
		sqlsession.insert(namespace+".RegisterInterferenceLocation", dto);
	}
	
	@Override
	public String FindVersionRequest(String version_interference) throws Exception {
		return sqlsession.selectOne(namespace+".FindVersionRequest", version_interference);
	}
	
	@Override
	public String DownloadLinkInterferenceCSV(Interference_detailDTO dto) throws Exception {
		return sqlsession.selectOne(namespace+".DownloadLinkInterferenceCSV", dto);
	}
	
	@Override
	public List<InterferenceDTO> interferenceChoiceList(InterferenceDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".interferenceChoiceList", dto);
	}
}
