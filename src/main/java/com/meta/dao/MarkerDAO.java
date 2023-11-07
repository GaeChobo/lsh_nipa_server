package com.meta.dao;

import java.util.List;

import com.meta.dto.MarkerDTO;

public interface MarkerDAO {

	public void MarkerImgDelete(MarkerDTO dto) throws Exception;
	
	public void MarkerDelete(MarkerDTO dto) throws Exception;
	
	public void MarkerUpdate(MarkerDTO dto) throws Exception;
	
	public List<MarkerDTO> AdminMarkerList(MarkerDTO dto) throws Exception;
	
	public List<MarkerDTO> AdminMarkerSearch(MarkerDTO dto) throws Exception;

	public void MarkerRegister(MarkerDTO dto) throws Exception;
	
	public List<MarkerDTO> MarkerFindValue(String marker_no) throws Exception;
}
