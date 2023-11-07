package com.meta.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.meta.dto.FacilityDTO;
import com.meta.vo.Message;

public interface FacilityService {

	public ResponseEntity<Message> FacilityTypeList() throws Exception;
	
	public void CsvDataUpload(FacilityDTO dto) throws Exception;
	
	public int RequestInterUserNum(FacilityDTO dto) throws Exception;
	
	public List<FacilityDTO> RequestInterUserList(FacilityDTO dto) throws Exception;
}
