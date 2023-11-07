package com.meta.dao;

import java.util.List;

import com.meta.dto.PlantDTO;

public interface PlantDAO {

	public List<PlantDTO> PlantMapList(String area_code) throws Exception;
	
	public List<PlantDTO> PlantMapZone1 (String area_code) throws Exception;
	
	public List<PlantDTO> PlantMapZone2 (String area_code) throws Exception;

	public List<PlantDTO> PlantMapZone3 (String area_code) throws Exception;
}
