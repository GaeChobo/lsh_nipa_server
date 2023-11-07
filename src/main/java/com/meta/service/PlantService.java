package com.meta.service;

import org.springframework.http.ResponseEntity;

import com.meta.vo.Message;

public interface PlantService {

	public ResponseEntity<Message> PlantMapList(String area_code) throws Exception;
	
}
