package com.meta.dao;

import java.util.List;

import com.meta.dto.FacilityDTO;

public interface FacilityDAO {

	public void CsvDataUpload(FacilityDTO dto) throws Exception;
	
	public List<FacilityDTO> sewage_pipe_List() throws Exception;
	
	public List<FacilityDTO> sewage_manhole_List() throws Exception;
	
	public List<FacilityDTO> dyke_List() throws Exception;
	
	public List<FacilityDTO> road_List() throws Exception;
	
	public List<FacilityDTO> sidewalk_List() throws Exception;
	
	public List<FacilityDTO> block_List() throws Exception;
	
	public List<FacilityDTO> facility_List() throws Exception;
	
	public List<FacilityDTO> drinking_fountain_List() throws Exception;
	
	public List<FacilityDTO> uprain_pipe_List() throws Exception;
	
	public List<FacilityDTO> uprain_manhole_List() throws Exception;
	
	public List<FacilityDTO> bicycle_road_List() throws Exception;
	
	public List<FacilityDTO> road_boundary_stone_List() throws Exception;
	
	public List<FacilityDTO> lane_List() throws Exception;
	
	public List<FacilityDTO> RequestInterUserList(FacilityDTO dto) throws Exception;
	
	public int RequestInterUserNum(FacilityDTO dto) throws Exception;
	
}
