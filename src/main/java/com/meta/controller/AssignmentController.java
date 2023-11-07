package com.meta.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meta.dto.AssignmentDTO;
import com.meta.service.AssignmentService;
import com.meta.vo.Message;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class AssignmentController {

	@Autowired
	AssignmentService assignmentService;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date time = new Date();
	
	String time1 = format1.format(time);
	
	private static final Logger logger = LoggerFactory.getLogger(AssignmentController.class);

	/*
	@ApiOperation(value="Admin 발주처 검색 - 사용안함", notes="admin 발주처 검색 입력" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "owenr_name", value = "발주처"),
	})
	@RequestMapping(value = "/OwenerList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> OwenerList(@RequestBody Map<String, String> owener_list) throws Exception {
		
		logger.info(time1 + " ( OwenerList )");

		return assignmentService.OwenerList(owener_list.get("owner_name"));
	}
	*/
	
	@ApiOperation(value="할당지역 이미지 조회", notes="area_code에 따른 이미지 조회" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "String")
	})
	@RequestMapping(value = "/AssignareaImgFind", method = RequestMethod.POST, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> AssignareaImgFind(@RequestBody AssignmentDTO dto) throws Exception {
		
		logger.info(time1 + " ( AssignareaImgFind )");

		return assignmentService.AssignareaImgFind(dto.getArea_code());
	}
	
	@ApiOperation(value="req_003 2. 선택완료 버튼", notes="2. 선택완료 버튼(https://www.notion.so/req_003-70d2cfa653f04fd78708e8fbbed76208)" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String"),
		@ApiImplicitParam(name = "area_code", value = "String")
	})
	@RequestMapping(value = "/SelectionComplete", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> SelectionComplete(@RequestBody AssignmentDTO dto) throws Exception {
		
		logger.info(time1 + " ( SelectionComplete )");
		
		return assignmentService.SelectionComplete(dto.getUser_id(), dto.getArea_code());
	}
		
	@ApiOperation(value="req_003 1. 지역 선택 리스트", notes="1. 지역 선택 리스트(https://www.notion.so/req_003-70d2cfa653f04fd78708e8fbbed76208)" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "유저ID"),
	})
	@RequestMapping(value = "/Listassignment", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> Listassignment(@RequestBody AssignmentDTO dto) throws Exception {
		
		logger.info(time1 + " ( Listassignment )");

		return assignmentService.Listassignment(dto.getUser_id());
	}
	
	@ApiOperation(value="회원 가입한 유저(사용자)에게 메타버스 접속 가능 지역 할당", notes="가입한 사용자에게 할당지역 등록 API" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "user_id", value = "String"),
		@ApiImplicitParam(name = "area_code", value = "String"),
		@ApiImplicitParam(name = "user_type", value = "String")
	})
	@RequestMapping(value = "/Registerassginment", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> Registerassginment(@RequestBody AssignmentDTO dto) throws Exception {
		
		logger.info(time1 + " ( Registerassginment )");

		return assignmentService.Registerassginment(dto.getUser_id(), dto.getArea_code(), dto.getUser_type());
	}
		
	@ApiOperation(value="edc_nipa_req_018", notes="우클릭 메뉴의 제공 서비스 - 현장 정보(https://www.notion.so/edc_nipa_req_018-259f2be4994e419c8556538fed0b9173)" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "String")
	})
	@RequestMapping(value = "/assignmentAreaDetail", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> assignmentAreaDetail(@RequestBody AssignmentDTO dto) throws Exception {
		
		logger.info(time1 + " ( assignmentAreaDetail )");

		return assignmentService.assignmentAreaDetail(dto.getArea_code());
	}
}

