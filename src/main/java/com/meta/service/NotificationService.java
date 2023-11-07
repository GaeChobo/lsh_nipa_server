package com.meta.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.meta.dto.SensorNotificaitonDTO;
import com.meta.vo.Message;

public interface NotificationService {

	public ResponseEntity<Message> NotificaitonUpdate(SensorNotificaitonDTO dto) throws Exception;
	
	public ResponseEntity<Message> NotificationCount(String target_user_id) throws Exception;
	
	public ResponseEntity<Message> NotificationList(String target_user_id) throws Exception;
}
