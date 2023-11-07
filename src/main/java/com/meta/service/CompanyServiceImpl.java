package com.meta.service;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.dao.CompanyDAO;
import com.meta.dao.UserDAO;
import com.meta.dto.CompanyDTO;
import com.meta.vo.ErrorEnum;
import com.meta.vo.Message;
import com.meta.vo.StatusEnum;

@Service
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	CompanyDAO companyDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Override
	public ResponseEntity<Message> CompanyDomainList() throws Exception {

		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		message.setResult("success");
		message.setMessage("성공");
		message.setStatus(StatusEnum.OK);
		message.setError_code(ErrorEnum.NONE);
		message.setData(companyDAO.CompanyDomainList());
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Message> OverlapID(String user_id, String domain_name) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		Integer ID = userDAO.OverlapID(user_id);
		String CompanyName = companyDAO.OverlapDomain(domain_name);
		
		if(CompanyName == null) {
			
			message.setResult("fail");
			message.setMessage("‘등록된 회사 정보가 없습니다. 시스템 관리자에게 문의 하세요’");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData("none");
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}else {
			
			if(ID < 1) {
			
				message.setResult("success");
				message.setMessage("사용할 수 있습니다.");
				message.setStatus(StatusEnum.OK);
				message.setData(CompanyName);
				message.setError_code(ErrorEnum.NONE);
				
				return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
			} else{
				
				message.setResult("fail");
				message.setMessage("사용할 수 없는 아이디입니다.");
				message.setStatus(StatusEnum.OK);
				message.setData("none");
				message.setError_code(ErrorEnum.NONE);
					
				return new ResponseEntity<>(message, headers, HttpStatus.OK);
			}
		}
	}
	
	@Override
	public ResponseEntity<Message> RegisterDomain(CompanyDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			companyDAO.RegisterDomain(dto);
			
			message.setError_code(ErrorEnum.NONE);
			message.setResult("success");
			message.setMessage("도메인 등록 완료");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
			
		}catch(Exception e) {
		
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setResult("fail");
			message.setMessage("도메인 등록 실패");
			message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
}
