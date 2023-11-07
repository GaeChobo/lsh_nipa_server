package com.meta.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.meta.dto.MarkerDTO;
import com.meta.service.MarkerService;
import com.meta.vo.Message;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class MarkerController {

	@Autowired
	MarkerService markerService;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date time = new Date();
	
	String time1 = format1.format(time);
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(MarkerController.class);
	
	
	@ApiOperation(value="Marker 이미지 출력", notes="marker_no 키값" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "marker_no", value = "String")
	})
	@RequestMapping(value = "/MarkerImgFind", method = RequestMethod.POST, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> MarkerImgFind(@RequestBody MarkerDTO dto) throws Exception {
		
		logger.info(time1 + " ( MarkerImgFind )");
		
		return markerService.MarkerImgFind(dto);
	}
	
	@ApiOperation(value="Marker 정보 조회", notes="marker_no 키값" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "marker_no", value = "String")
	})
	@RequestMapping(value = "/MarkerFindValue", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> MarkerFindValue(@RequestBody MarkerDTO dto) throws Exception {
		
		logger.info(time1 + " ( MarkerFindValue )");
		
		return markerService.MarkerFindValue(dto);
	}
	
	@ApiOperation(value="Qr 마커 생성 타입1", notes="Marker값이 QR에 전부 담겨져있음" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "String"),
		@ApiImplicitParam(name = "zone_name", value = "String"),
		@ApiImplicitParam(name = "section_name", value = "String"),
		@ApiImplicitParam(name = "maker_key", value = "String"),
		@ApiImplicitParam(name = "marker_detail", value = "String"),
		@ApiImplicitParam(name = "x", value = "double"),
		@ApiImplicitParam(name = "y", value = "double"),
		@ApiImplicitParam(name = "z", value = "double")
	})
	@RequestMapping(value = "/MarkerRegister1", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> MarkerRegister1(@RequestBody MarkerDTO dto) throws Exception {
		
		logger.info(time1 + " ( MarkerRegister1 )");
		
		return markerService.MarkerRegister1(dto);
	}
	
	@ApiOperation(value="Qr 마커 생성 타입2", notes="marker_no값만 QR에 담겨져있음 -> 후속 조회로" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "String"),
		@ApiImplicitParam(name = "zone_name", value = "String"),
		@ApiImplicitParam(name = "section_name", value = "String"),
		@ApiImplicitParam(name = "maker_key", value = "String"),
		@ApiImplicitParam(name = "marker_detail", value = "String"),
		@ApiImplicitParam(name = "x", value = "double"),
		@ApiImplicitParam(name = "y", value = "double"),
		@ApiImplicitParam(name = "z", value = "double")
	})
	@RequestMapping(value = "/MarkerRegister2", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> MarkerRegister2(@RequestBody MarkerDTO dto) throws Exception {
		
		logger.info(time1 + " ( MarkerRegister2 )");
		
		return markerService.MarkerRegister2(dto);
	}
}
