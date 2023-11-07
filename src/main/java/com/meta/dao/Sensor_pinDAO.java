package com.meta.dao;

import java.util.List;

import com.meta.dto.Sensor_pinDTO;

public interface Sensor_pinDAO {

	public void RegisterPinSensor(Sensor_pinDTO dto) throws Exception;
	
	public void DeletePinSensor(Sensor_pinDTO dto) throws Exception;
	
	public List<Sensor_pinDTO> SelectListPin(Sensor_pinDTO dto) throws Exception;

}
