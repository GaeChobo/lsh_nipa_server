package com.meta.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meta.dto.FacilityDTO;

public class FacilityDAOImpl implements FacilityDAO {

	@Autowired
	@Qualifier(value = "OneSqlSession")
	private SqlSession sqlsession;
	
	public static final String namespace = "mappers.facilityMapper";

	@Override
	public int RequestInterUserNum(FacilityDTO dto) throws Exception {
		return sqlsession.selectOne(namespace+".RequestInterUserNum", dto);
	}
	
	@Override
	public List<FacilityDTO> RequestInterUserList(FacilityDTO dto) throws Exception {
		
		return sqlsession.selectList(namespace+".RequestInterUserList", dto);
	}
	
	@Override
	public void CsvDataUpload(FacilityDTO dto) throws Exception {
		
		sqlsession.insert(namespace+".CsvDataUpload", dto);
	}

	@Override
	public List<FacilityDTO> sewage_pipe_List() throws Exception {
		return sqlsession.selectList(namespace+".sewage_pipe_List");
	}
	
	@Override
	public List<FacilityDTO> sewage_manhole_List() throws Exception {
		return sqlsession.selectList(namespace+".sewage_manhole_List");
	}
	
	@Override
	public List<FacilityDTO> dyke_List() throws Exception {
		return sqlsession.selectList(namespace+".dyke_List");
	}
	
	@Override
	public List<FacilityDTO> road_List() throws Exception {
		return sqlsession.selectList(namespace+".road_List");
	}
	
	@Override
	public List<FacilityDTO> sidewalk_List() throws Exception {
		return sqlsession.selectList(namespace+".sidewalk_List");
	}
	
	@Override
	public List<FacilityDTO> block_List() throws Exception {
		return sqlsession.selectList(namespace+".block_List");
	}
	
	@Override
	public List<FacilityDTO> facility_List() throws Exception {
		return sqlsession.selectList(namespace+".facility_List");
	}
	
	@Override
	public List<FacilityDTO> drinking_fountain_List() throws Exception {
		return sqlsession.selectList(namespace+".drinking_fountain_List");
	}
	
	@Override
	public List<FacilityDTO> uprain_pipe_List() throws Exception {
		return sqlsession.selectList(namespace+".uprain_pipe_List");
	}
	
	@Override
	public List<FacilityDTO> uprain_manhole_List() throws Exception {
		return sqlsession.selectList(namespace+".uprain_manhole_List");

	}
	
	@Override
	public List<FacilityDTO> bicycle_road_List() throws Exception {
		return sqlsession.selectList(namespace+".bicycle_road_List");
	}
	
	@Override
	public List<FacilityDTO> road_boundary_stone_List() throws Exception {
		return sqlsession.selectList(namespace+".road_boundary_stone_List");
	}
	
	@Override
	public List<FacilityDTO> lane_List() throws Exception {
		return sqlsession.selectList(namespace+".lane_List");
	}
	
	
}
