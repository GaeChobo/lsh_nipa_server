package com.meta.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.meta.dto.MarkerDTO;
import com.meta.vo.Message;

public interface MarkerService {

	public String AdminMarkerImgDownLoad(String marker_no) throws Exception;
	
	public ResponseEntity<Message> AdminMarkerImgDelete(MarkerDTO dto) throws Exception;

	public ResponseEntity<Message> AdminMarkerDelete(MarkerDTO dto) throws Exception;
	
	public ResponseEntity<Message> AdminMarkerUpdate(MarkerDTO dto) throws Exception;

	public ResponseEntity<Message> AdminMarkerRegister(MarkerDTO dto) throws Exception;
	
	public ResponseEntity<Message> AdminMarkerList(MarkerDTO dto) throws Exception;
	
	public ResponseEntity<Message> AdminMarkerSearch(MarkerDTO dto) throws Exception;
	
	public ResponseEntity<Message> MarkerRegister1(MarkerDTO dto) throws Exception;

	public ResponseEntity<Message> MarkerRegister2(MarkerDTO dto) throws Exception;
	
	public ResponseEntity<Message> MarkerFindValue(MarkerDTO dto) throws Exception;
	
	public ResponseEntity<byte[]> MarkerImgFind(MarkerDTO dto) throws Exception;

}
