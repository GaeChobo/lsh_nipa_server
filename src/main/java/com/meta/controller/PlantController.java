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

import com.meta.service.PlantService;
import com.meta.vo.Message;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class PlantController {

	@Autowired
	PlantService plantservice;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date time = new Date();

	String time1 = format1.format(time);
	
	private static final Logger logger = LoggerFactory.getLogger(PlantController.class);

	@ApiOperation(value="edc_nipa_req_006 구역 이동 정보 제공", notes="(https://www.notion.so/0afdae02fe6148498f97e737c19433ee?p=ce23935c068f402a87a989aeee12f36e&pm=s)" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "area_code", value = "String")
	})
	@RequestMapping(value = "/PlantMapList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> PlantMapList(@RequestBody Map<String, String> area_map) throws Exception {
		
		logger.info(time1 + " ( PlantMapList )");
		
		return plantservice.PlantMapList(area_map.get("area_code"));
		
	}

}
