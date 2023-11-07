package com.meta.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.meta.service.CctvService;
import com.meta.vo.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@Api(tags = {"cctv 컨트롤러"})
@RequestMapping("/api")
public class CctvController {

	@Autowired
	CctvService cctvService;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	Date time = new Date();
	
	String time1 = format1.format(time);
	
	private static final Logger logger = LoggerFactory.getLogger(CctvController.class);


	@ApiOperation(value="CCTV 이미지 조회", notes="CCTV 이미지 조회" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "cctv_no", value = "String", required = true)
	})
	@RequestMapping(value = "/CCTVImageFindList", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> CCTVImageFindList(@RequestParam String cctv_no) throws Exception {
		
		logger.info(time1 + " ( CCTVImageFindList )");
		
		return cctvService.CCTVImageFindList(cctv_no);
	}
	
	@ApiOperation(value="CCTV 스틸컷 및 들어올 API", notes="파일형식 [CCTV_NO1, CCTV_NO2, CCTV_NO3, CCTV_NO4]" , produces = "application/json;charset=utf-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "path_still_cut", value = "File", required = true),
	})
	@RequestMapping(value = "/RegisterCCTV", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public ResponseEntity<Message> RegisterCCTV(MultipartHttpServletRequest request) throws Exception {
		
		logger.info(time1 + " ( RegisterCCTV )");
		
		return cctvService.RegisterCCTV(request);
	}
	
	@Scheduled(cron = "0 0 */9 * * *")
	public void SensorDelete() throws Exception {
		
		logger.info(time1 + " ( CCTVAllDelete )");
		
		cctvService.CCTVAllDelete();
		
	}
	
}
