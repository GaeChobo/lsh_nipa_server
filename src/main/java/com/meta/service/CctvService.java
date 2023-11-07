package com.meta.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.meta.dto.CctvDTO;
import com.meta.vo.Message;


public interface CctvService {

	public void CCTVAllDelete() throws Exception;
	
	public ResponseEntity<Message> AdminCctvSearch(CctvDTO dto) throws Exception;
	
	public ResponseEntity<Message> AdminCCTVList(CctvDTO dto) throws Exception;
	
	public ResponseEntity<byte[]> CCTVImageFindList(String cctv_no) throws Exception;
	
	public ResponseEntity<Message> RegisterCCTV(MultipartHttpServletRequest request) throws Exception;
}
