package com.meta.service;

import org.springframework.http.ResponseEntity;


import com.meta.dto.CompanyDTO;
import com.meta.vo.Message;

public interface CompanyService {

	public ResponseEntity<Message> CompanyDomainList() throws Exception;
	
	public ResponseEntity<Message> OverlapID(String user_id, String domain_name) throws Exception;
	
	public ResponseEntity<Message> RegisterDomain(CompanyDTO dto) throws Exception;
	
}
