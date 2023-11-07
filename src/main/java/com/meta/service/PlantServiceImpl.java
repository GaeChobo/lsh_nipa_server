package com.meta.service;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.dao.PlantDAO;
import com.meta.dto.PlantDTO;
import com.meta.dto.ZoneDTO;
import com.meta.vo.ErrorEnum;
import com.meta.vo.Message;
import com.meta.vo.StatusEnum;

@Service
public class PlantServiceImpl implements PlantService {

	@Autowired
	PlantDAO dao;
	
	@Override
	public ResponseEntity<Message> PlantMapList(String area_code) throws Exception {
	
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {

			List<PlantDTO> zoenList1 = dao.PlantMapZone1(area_code);
			List<PlantDTO> zoenList2 = dao.PlantMapZone2(area_code);
			List<PlantDTO> zoenList3 = dao.PlantMapZone3(area_code);

			
			ZoneDTO zonedto = new ZoneDTO();
			
			zonedto.setZone_1(zoenList1);
			zonedto.setZone_2(zoenList2);
			zonedto.setZone_3(zoenList3);
			
			message.setResult("success");
			message.setMessage("조회 성공");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(zonedto);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

		} catch(Exception e) {
			
			message.setResult("fail");
			message.setMessage("해당 옥내 시설이 없습니다.");
			message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

		}
	}
}
