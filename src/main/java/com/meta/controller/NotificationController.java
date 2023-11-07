package com.meta.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meta.dto.SensorNotificaitonDTO;
import com.meta.service.NotificationService;
import com.meta.service.SensorService;
import com.meta.vo.Message;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class NotificationController {

	
	@Autowired
	NotificationService notiService;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date time = new Date();
	
	String time1 = format1.format(time);
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

	@ApiOperation(value="알림 상세 정보 ‘확인’ 버튼", notes="edc_nipa_req_005 API : 읽음 처리 " , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "no", value = "int"),
		@ApiImplicitParam(name = "target_user_id", value = "String")
	})
	@RequestMapping(value = "/NotificaitonUpdate", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> NotificaitonUpdate(@RequestBody SensorNotificaitonDTO dto) throws Exception {
		
		logger.info(time1 + " ( NotificaitonUpdate )");
		
		return notiService.NotificaitonUpdate(dto);
	}
	
	@ApiOperation(value="알림 기능", notes="edc_nipa_req_005 API : 사용자에게 할당된 알림 메세지 중 안읽음(read=0) 데이터 수 제공" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "target_user_id", value = "String"),
	})
	@RequestMapping(value = "/NotificationCount", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> NotificationCount(@RequestBody Map<String, String> notilist) throws Exception {
		
		logger.info(time1 + " ( NotificationCount )");
		
		return notiService.NotificationCount(notilist.get("target_user_id"));
	}
	
	@ApiOperation(value="알림 메세지 상세 리스트", notes="edc_nipa_req_005 API : 사용자 알림 메세지 리스트 제공" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "target_user_id", value = "String"),
	})
	@RequestMapping(value = "/NotificationList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> NotificationList(@RequestBody Map<String, String> notilist) throws Exception {
		
		logger.info(time1 + " ( NotificationList )");
		
		return notiService.NotificationList(notilist.get("target_user_id"));
	}
	
}
