package com.meta.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meta.dto.CSensorDTO;
import com.meta.dto.GasSendDTO;
import com.meta.dto.Result_SensorDTO;
import com.meta.dto.Sensor_pinDTO;
import com.meta.dto.UserDTO;
import com.meta.service.SensorService;
import com.meta.vo.Message;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@EnableScheduling
@RequestMapping("/api")
public class SensorController {

	@Autowired
	SensorService sensorService;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date time = new Date();
	
	String time1 = format1.format(time);
	
	private static final Logger logger = LoggerFactory.getLogger(SensorController.class);
	
	
	@ApiOperation(value="센서 대쉬보드 파라미터값 처리", notes="ALL인 경우 zone_name : String 공백값, section_name :  String 공백값" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "zone_name", value = "String"),
		@ApiImplicitParam(name = "section_name", value = "String")
	})
	@RequestMapping(value = "/SensorInventory", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> SensorInventory(@RequestBody CSensorDTO dto) throws Exception {
		
		logger.info(time1 + " ( SensorInventory )");
		
		return sensorService.SensorInventory2(dto);
		
	}
	
	@ApiOperation(value="인공지능 모델 이상감지 데이터 등록", notes="인공지능 모델 감지 등록" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "sensor_name", value = "String[]")
	})
	@RequestMapping(value = "/AISensorInsert", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> AISensorInsert(@RequestBody Map<String, String[]> sensor_name) throws Exception {
		
		logger.info(time1 + " ( AISensorInsert )");
		
		return sensorService.AISensorInsert(sensor_name.get("sensor_name"));
	}
	
	
	@ApiOperation(value="가스센서 리스트 조회", notes="인공지능에 내려줄 가스센서 조회 ( default로 사용시 number_of_data : null로 처리 )" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "number_of_data", value = "int")
	})
	@RequestMapping(value = "/GasList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> GasList(@RequestBody Map<String, Integer> number_of_data) throws Exception {
		
		logger.info(time1 + " ( GasList )");
		
		sensorService.AssginTest();
		
		return sensorService.GasList(number_of_data.get("number_of_data"));
	}
	
	
	
	@ApiOperation(value="센서 핀 조회", notes="센서 조회 창 진입 시 request or 등록 혹은 해제 시 request" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String")
	})
	@RequestMapping(value = "/SelectListPin", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> SelectListPin(@RequestBody Sensor_pinDTO dto) throws Exception {
		
		logger.info(time1 + " ( SelectListPin )");
		
		return sensorService.SelectListPin(dto);
	}
	
	@ApiOperation(value="센서 그래프 조회", notes="대쉬보드 센서 선택 시 그래프 사용 용도" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "sensor_name", value = "String")
	})
	@RequestMapping(value = "/SensorGraphList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> SensorGraphList(@RequestBody Result_SensorDTO dto) throws Exception {
		
		logger.info(time1 + " ( SensorGraphList )");
		
		return sensorService.SensorGraphList(dto);
	}
	
	
	@ApiOperation(value="지정 센서 조회", notes="단일 센서 조회" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "sensor_name", value = "String")
	})
	@RequestMapping(value = "/OneSensorList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> OneSensorList(@RequestBody Result_SensorDTO dto) throws Exception {
		
		logger.info(time1 + " ( OneSensorList )");
		
		return sensorService.OneSensorList(dto);
	}
	

	/*
	@RequestMapping(value = "/SensorInventory", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> SensorInventory() throws Exception {
		
		logger.info(time1 + " ( SensorInventory )");
		
		return sensorService.SensorInventory();
	}
	*/
	
	@Scheduled(cron = "0 0 */9 * * *")
	public void SensorDelete() throws Exception {
	
		logger.info(time1 + " ( SensorDelete )");
		
		sensorService.SensorDelete();
	}
	
	@Scheduled(cron = "0 */1 * * * *")
	public void SensorTest() throws Exception {
		
		logger.info(time1 + " ( SensorTest )");
		
		sensorService.SensorTest();
	
		sensorService.SensorTest2();
	}

	/*
	@RequestMapping(value = "/ValueSensorList", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> ValueSensorList() throws Exception {
		
		logger.info(time1 + " ( ValueSensorList )");
		
		return sensorService.ValueSensorList();
	}
	
	*/
	@ApiOperation(value="nipa_req_001 메인 대쉬보드 API", notes="ss_stat [센서 계측값 정상 : 00 , 경고 1단계(alram 1) : 01, 경고 2단계(alram 2) : 02, 경고 3단계(alram 3) : 03, 관계무 : 09, CCTV 컷 : 20, CCTV 컷 : 20], [변수 '값' 매칭되는 거 없을 때 값 : -9999 ], [변수 '계측시간' 00:00으로 표시" , produces = "application/json;charset=utf-8")
	@RequestMapping(value = "/DefaultSensorList", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> DefaultSensorList() throws Exception {
		
		logger.info(time1 + " ( DefaultSensorList )");
		
		return sensorService.DefaultSensorList();
	}

	@ApiOperation(value="nipa_req_001 메인 대쉬보드 API - 사용자 핀 등록", notes="사용자 핀 등록 후 핀 조회 다시 request" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String"),
		@ApiImplicitParam(name = "sensor_name", value = "String")
	})
	@RequestMapping(value = "/RegisterPinSensor", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> RegisterPinSensor(@RequestBody Sensor_pinDTO dto) throws Exception {
		
		logger.info(time1 + " ( RegisterPinSensor )");
		
		return sensorService.RegisterPinSensor(dto);
	}
	
	@ApiOperation(value="nipa_req_001 메인 대쉬보드 API - 사용자 핀 삭제", notes="사용자 핀 삭제 후 핀 조회 다시 request" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String"),
		@ApiImplicitParam(name = "sensor_name", value = "String")
	})
	@RequestMapping(value = "/DeletePinSensor", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> DeletePinSensor(@RequestBody Sensor_pinDTO dto) throws Exception {
		
		logger.info(time1 + " ( DeletePinSensor )");
		
		return sensorService.DeletePinSensor(dto);
	}
	
	@ApiOperation(value="nipa_req_001 메인 대쉬보드 API - 센서 value값 조회", notes="센서 value값만 조회" , produces = "application/json;charset=utf-8")
	@RequestMapping(value = "/ValueSensorList", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> ValueSensorList() throws Exception {
		
		logger.info(time1 + " ( ValueSensorList )");
		
		return sensorService.ValueSensorList();
	}
	
	@ApiOperation(value="nipa_req_001 메인 대쉬보드 API - 센서 Default값 조회", notes="센서 Default값만 조회" , produces = "application/json;charset=utf-8")
	@RequestMapping(value = "/BasicSensorDataList", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> BasicSensorDataList() throws Exception {
		
		logger.info(time1 + " ( BasicSensorDataList )");
		
		return sensorService.BasicSensorDataList();
	}
}
