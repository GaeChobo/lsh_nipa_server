package com.meta.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.dao.AssignmentDAO;
import com.meta.dto.AreaDTO;
import com.meta.dto.AssignmentDTO;
import com.meta.vo.ErrorEnum;
import com.meta.vo.Message;
import com.meta.vo.StatusEnum;

@Service
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	AssignmentDAO Assignmentdao;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date time = new Date();

	String time1 = format1.format(time);
	
	
	@Override
	public ResponseEntity<Message> AdminAreaInfo(AreaDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<AreaDTO> result = Assignmentdao.AdminAreaInfo(dto);
		
		if(result.size() > 0) {
			
			message.setResult("success");
			message.setMessage("현장 정보 조회 성공");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

			
		}else {
			
			message.setResult("fail");
			message.setMessage("현장 정보 없음");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

		}
	}

	
	@Override
	public ResponseEntity<Message> OwenerList(AreaDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<String> result = Assignmentdao.OwenerList(dto);
				
		if(result.size() > 0) {
			
			message.setResult("success");
			message.setMessage("발주처 리스트 있음");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

			
		}else {
			
			message.setResult("fail");
			message.setMessage("검색 조건 없음");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

		}
		
	}
	
	@Override
	public ResponseEntity<byte[]> AssignareaImgFind(String area_code) throws Exception {
		
		try {
			
			String fileName = null;
			
			if(area_code.equals("mk_factory_ansan_1")) {
				
				fileName = ("/home/ubuntu/files/area_image/ansan_mokdong.png");
				
			}else if (area_code.equals("EDC_1_2")) {
				
				fileName = ("/home/ubuntu/files/area_image/EDC_1_2.png");
				
			}else if(area_code.equals("EDC_1_3")) {
				
				fileName = ("/home/ubuntu/files/area_image/EDC_1_3.png");
			}
			
	        InputStream imageStream = new FileInputStream(fileName);
	        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
	        imageStream.close();
			
	        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
			
		} catch(Exception e) {
			
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public ResponseEntity<Message> Listassignment(String user_id) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<AssignmentDTO> result = Assignmentdao.Listassignment(user_id);
		
		System.out.println(result.size());
		
		if(result.size() > 0) {
			
			message.setResult("success");
			message.setMessage("할당 지역 있음");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

			
		}else {
			
			message.setResult("fail");
			message.setMessage("할당 지역이 없습니다. 시스템 관리자에게 문의 하세요");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

			
		}
	}

	@Override
	public ResponseEntity<Message> SelectionComplete(String user_id, String area_code) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<AssignmentDTO> result = Assignmentdao.SelectionComplete(user_id,area_code);
		
		if(result.size() > 0) {
			
			message.setResult("success");
			message.setMessage("사용자 정보 확인 성공");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

			
		}else {
			
			message.setResult("fail");
			message.setMessage("사용자 정보 확인 실패, 다시 지역 선택을 해주세요");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<Message> Registerassginment(String user_id,String area_code, int user_type) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		AssignmentDTO dto = new AssignmentDTO();
		
		try {	
		
			Calendar cal = Calendar.getInstance();
			
			cal.setTime(time);
			
			cal.add(Calendar.HOUR, 9);
			cal.add(Calendar.YEAR, 5);
		
			String path = null;
			
			if(area_code.equals("mk_factory_ansan_1")) {
				
				path = ("http://13.209.48.128:8080/areaImage/ansan_mokdong.png");
				
			}else if (area_code.equals("EDC_1_2")) {
				
				path = ("http://13.209.48.128:8080/areaImage/EDC_1_2.png");
				
			}else if(area_code.equals("EDC_1_3")) {
				
				path = ("http://13.209.48.128:8080/areaImage/EDC_1_3.png");
			}
			
			dto.setUser_type(user_type);
			dto.setPath_img(path);
			dto.setUser_id(user_id);
			dto.setArea_code(area_code);
			dto.setExpiration_datetime(cal.getTime());

			Assignmentdao.Registerassginment(dto);
			
			message.setResult("success");
			message.setMessage("등록 성공");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

		
		} catch(Exception e) {
		
			
			message.setResult("fail");
			message.setMessage("등록 실패");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setData(null);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);

		}
	}

	@Override
	public ResponseEntity<Message> assignmentAreaDetail(String area_code) throws Exception {
	
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<AreaDTO> result = Assignmentdao.assignmentAreaDetail(area_code);
		
		if(result.size() > 0) {
			
			message.setError_code(ErrorEnum.NONE);
			message.setResult("success");
			message.setMessage("조회 성공");
			message.setStatus(StatusEnum.OK);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} else {
			
			message.setError_code(ErrorEnum.NONE);
			message.setResult("fail");
			message.setMessage("현장 속성 정보 조회 실패");
			message.setStatus(StatusEnum.OK);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
}
