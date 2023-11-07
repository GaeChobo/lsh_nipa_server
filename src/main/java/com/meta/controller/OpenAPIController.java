package com.meta.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meta.dto.CSensorDTO;
import com.meta.service.SensorService;
import com.meta.vo.Message;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class OpenAPIController {

	@Autowired
	SensorService sensorService;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date time = new Date();
	
	String time1 = format1.format(time);
	
	private static final Logger logger = LoggerFactory.getLogger(OpenAPIController.class);
	
	@ApiOperation(value="주빅스 DB에서 데이터 가져올때 경고 3단계 일 경우", notes="" , produces = "application/json;charset=utf-8")
	@RequestMapping(value = "/warningSensor", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> warningSensor() throws Exception {
		
		logger.info(time1 + " ( warningSensor )");
		
		return sensorService.warningSensor();
		
	}
	
	@ApiOperation(value="인공지능 모델에서 이상감지 했을 경우", notes="" , produces = "application/json;charset=utf-8")
	@RequestMapping(value = "/AIwarningSensor", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> AIwarningSensor() throws Exception {
		
		logger.info(time1 + " ( AIwarningSensor )");
		
		return sensorService.AIwarningSensor();
		
	}
	
	@ApiOperation(value="CCTV Event 컷 ", notes="" , produces = "application/json;charset=utf-8")
	@RequestMapping(value = "/warningCCTV", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> warningCCTV() throws Exception {
		
		logger.info(time1 + " ( warningCCTV )");
		
		return sensorService.warningCCTV();
		
	}
	
	@ApiOperation(value="당일 이벤트 알림 메세지 전부 조회", notes="" , produces = "application/json;charset=utf-8")
	@RequestMapping(value = "/warningDailyMessage", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> warningDailyMessage() throws Exception {
		
		logger.info(time1 + " ( warningDailyMessage )");
		
		return sensorService.warningDailyMessage();
		
	}
	
}
