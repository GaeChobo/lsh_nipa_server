package com.meta.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meta.dto.PlantDTO;

public class PlantDAOImpl implements PlantDAO {

	@Autowired
	@Qualifier(value = "OneSqlSession")
	private SqlSession sqlsession;
	
	public static final String namespace = "mappers.plantMapper";

	@Override
	public List<PlantDTO> PlantMapList(String area_code) throws Exception {
		return sqlsession.selectList(namespace+".PlantMapList", area_code);
	}

	@Override
	public List<PlantDTO> PlantMapZone1 (String area_code) throws Exception {
		return sqlsession.selectList(namespace+".PlantMapZone1", area_code);
	}
	
	@Override
	public List<PlantDTO> PlantMapZone2 (String area_code) throws Exception {
		return sqlsession.selectList(namespace+".PlantMapZone2", area_code);
	}

	@Override
	public List<PlantDTO> PlantMapZone3 (String area_code) throws Exception {
		return sqlsession.selectList(namespace+".PlantMapZone3", area_code);
	}
	
	
}
