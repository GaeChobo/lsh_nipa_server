package com.meta.service;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.meta.dto.AreaDTO;
import com.meta.vo.Message;

public interface AssignmentService {

	public ResponseEntity<Message> AdminAreaInfo(AreaDTO dto) throws Exception;
	
	public ResponseEntity<Message> OwenerList(AreaDTO dto) throws Exception;
	
	public ResponseEntity<byte[]> AssignareaImgFind(@RequestParam String area_code) throws Exception;
	
	public ResponseEntity<Message> SelectionComplete(String user_id, String area_code) throws Exception;

	public ResponseEntity<Message> Listassignment(String user_id) throws Exception;

	public ResponseEntity<Message> Registerassginment(String user_id,String area_code, int user_type) throws Exception;

	public ResponseEntity<Message> assignmentAreaDetail(String area_code) throws Exception;

}
