package com.meta.service;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.dao.SensorNotificationDAO;
import com.meta.dto.SensorNotificaitonDTO;
import com.meta.vo.ErrorEnum;
import com.meta.vo.Message;
import com.meta.vo.StatusEnum;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	SensorNotificationDAO sensornotidao;
	
	@Override
	public ResponseEntity<Message> NotificaitonUpdate(SensorNotificaitonDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			sensornotidao.NotificaitonUpdate(dto);
			
			message.setResult("success");
			message.setStatus(StatusEnum.OK);
			message.setMessage("성공");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setMessage("실패");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@Override
	public ResponseEntity<Message> NotificationCount(String target_user_id) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		int result = sensornotidao.NotificationCount(target_user_id);
	
		if(result == 0) {
			
			message.setResult("fail");
			message.setStatus(StatusEnum.OK);
			message.setMessage("읽지 않은 알림 메세지가 없습니다.");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}else {
			
			
			message.setResult("success");
			message.setStatus(StatusEnum.OK);
			message.setMessage("읽지 않은 알림 메세지가 있습니다.");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<Message> NotificationList(String target_user_id) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		
		List<SensorNotificaitonDTO> result = sensornotidao.NotificationList(target_user_id);
		
		
		System.out.println(target_user_id);

		if(result.size() == 0) {
			
			System.out.println("here");
			
			message.setResult("fail");
			message.setStatus(StatusEnum.OK);
			message.setMessage("알림 메시지가 없습니다.");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}else {

			System.out.println("here1");
			
			message.setResult("success");
			message.setStatus(StatusEnum.OK);
			message.setMessage("알림 메시지 조회 성공");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}
		

	}
	
}
