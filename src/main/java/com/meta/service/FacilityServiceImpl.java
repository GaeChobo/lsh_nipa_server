package com.meta.service;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.dao.FacilityDAO;
import com.meta.dto.FacilityDTO;
import com.meta.dto.FacilitytypeDTO;
import com.meta.vo.ErrorEnum;
import com.meta.vo.Message;
import com.meta.vo.StatusEnum;

@Service
public class FacilityServiceImpl implements FacilityService  {
	
	@Autowired
	FacilityDAO facilityDAO;
	
	@Override
	public ResponseEntity<Message> FacilityTypeList() throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		FacilitytypeDTO typedto = new FacilitytypeDTO();
		
		try {
			
			typedto.setSewage_pipe(facilityDAO.sewage_pipe_List());
			typedto.setSewage_manhole(facilityDAO.sewage_manhole_List());
			typedto.setDyke(facilityDAO.dyke_List());
			typedto.setRoad(facilityDAO.road_List());
			typedto.setSidewalk(facilityDAO.sidewalk_List());
			typedto.setBlock(facilityDAO.block_List());
			typedto.setFacility(facilityDAO.facility_List());
			typedto.setDrinking_fountain(facilityDAO.drinking_fountain_List());
			typedto.setUprain_pipe(facilityDAO.uprain_pipe_List());
			typedto.setUprain_manhole(facilityDAO.uprain_manhole_List());
			typedto.setBicycle_road(facilityDAO.bicycle_road_List());
			typedto.setRoad_boundary_stone(facilityDAO.road_boundary_stone_List());
			typedto.setLane(facilityDAO.lane_List());
			
			message.setResult("success");
			message.setError_code(ErrorEnum.NONE);
			message.setStatus(StatusEnum.OK);
			message.setMessage("시설물 조회 성공");
			message.setData(typedto);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK); 
			
		} catch(Exception e){
			
			message.setResult("fail");
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setMessage("시설물 조회 실패");
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public void CsvDataUpload(FacilityDTO dto) throws Exception {
		facilityDAO.CsvDataUpload(dto);
	}
	
	@Override
	public int RequestInterUserNum(FacilityDTO dto) throws Exception {
		return facilityDAO.RequestInterUserNum(dto);
	}

	@Override
	public List<FacilityDTO> RequestInterUserList(FacilityDTO dto) throws Exception {
		return facilityDAO.RequestInterUserList(dto);
	}
}
