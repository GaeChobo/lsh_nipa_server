package com.meta.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meta.dto.Sensor_pinDTO;

public class Sensor_pinDAOImpl implements Sensor_pinDAO {

	@Autowired
	@Qualifier(value = "OneSqlSession")
	private SqlSession sqlsession;
	
	public static final String namespace = "mappers.sensor_pinMapper";
	
	@Override
	public void RegisterPinSensor(Sensor_pinDTO dto) throws Exception {
		sqlsession.insert(namespace+".RegisterPinSensor", dto);
	}
	
	@Override
	public void DeletePinSensor(Sensor_pinDTO dto) throws Exception {
		sqlsession.delete(namespace+".DeletePinSensor", dto);
	}
	
	@Override
	public List<Sensor_pinDTO> SelectListPin(Sensor_pinDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".SelectListPin", dto);
	}
	
}
