package com.meta.service;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.meta.dao.AssignmentDAO;
import com.meta.dao.CompanyDAO;
import com.meta.dao.UserDAO;
import com.meta.dto.AdminAreaDTO;
import com.meta.dto.AdminCompanyDTO;
import com.meta.dto.AreaDTO;
import com.meta.dto.AssginUserDTO;
import com.meta.dto.AssignListDTO;
import com.meta.dto.AssignmentDTO;
import com.meta.dto.CompanyDTO;
import com.meta.dto.HomeDataRetrunDTO;
import com.meta.dto.TimeDTO;
import com.meta.dto.UserDTO;
import com.meta.dto.UserSendDTO;
import com.meta.vo.ErrorEnum;
import com.meta.vo.HomeMessage;
import com.meta.vo.Message;
import com.meta.vo.StatusEnum;
import com.meta.vo.UserMessage;

import ch.qos.logback.classic.Logger;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	CompanyDAO companyDAO;
	
	@Autowired
	AssignmentDAO assignmentDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Override
	public ResponseEntity<Message> OwenerSelectione() throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<String> result = assignmentDAO.OwenerSelectione();
		
		if(result.size() > 0) {
			
			message.setResult("success");
			message.setMessage("발주처 리스트 있음");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData(result);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

			
		}else {
			
			message.setResult("fail");
			message.setMessage("발주처 리스트 없음");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);

		}
	}
	
	@Override
	public ResponseEntity<HomeMessage> loadingHomeData(String user_id) throws Exception {
		
		HomeMessage HMessage = new HomeMessage();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

		AdminCompanyDTO compdto = new AdminCompanyDTO();
		
		AreaDTO areadto = new AreaDTO();

	
		
			if(user_id.contains("@")) {
				
				StringBuffer domainName = new StringBuffer(user_id);
				
				int Domain_len = user_id.indexOf('@');
				
				//도메인명
				StringBuffer domain = domainName.delete(0, Domain_len+1);
				
				System.out.println(domain.toString());
				
				if(domain.toString().equals("movements.kr")) {

					compdto.setDomain_name("");
					
					areadto.setArea_code("");
					
					List<AreaDTO> SiteResult = assignmentDAO.AdminAreaInfo(areadto);
					
					List<AdminCompanyDTO> result = companyDAO.DomainList(compdto);

					List<String> CompanyNameList = companyDAO.CompanyNameList();
					
					List<List<String>> totalList = new ArrayList<>();
					
					for(int i = 0; i < CompanyNameList.size(); i++) {
						
						List<String> area_list = assignmentDAO.AdminAreaList(CompanyNameList.get(i));
						
						totalList.add(i, area_list);
						
					}
					
					for(int i = 0; i < totalList.size(); i++) {
						
						if(totalList.get(i).isEmpty()) {
							
							System.out.println("null");
							
							result.get(i).setArea_code(null);
							
						} else {
							
							result.get(i).setArea_code(totalList.get(i));
							
						}
					
 					}
					
					HMessage.setResult("success");
					HMessage.setMessage("조회 성공");
					HMessage.setError_code(ErrorEnum.NONE);
					
					HMessage.setMovements(1);
					
					HMessage.setCompanydata(result);
					
					HMessage.setSitedata(SiteResult);
					
				}else {

					String CompanyName = companyDAO.CompanyNameFind(user_id);
					
					AreaDTO adto = new AreaDTO();
					
					adto.setCompany_name(CompanyName);

					compdto.setDomain_name(domain.toString());
					
					HMessage.setResult("success");
					HMessage.setMessage("조회 성공");
					HMessage.setError_code(ErrorEnum.NONE);
					
					HMessage.setMovements(0);
					
					List<AreaDTO> SiteResult = assignmentDAO.CompanyNameFindList(adto);
					
					//List<AreaDTO> SiteResult = assignmentDAO.AdminAreaInfo(areadto);
					
					List<AdminCompanyDTO> result = companyDAO.DomainList(compdto);
					
					List<String> area_list = assignmentDAO.AdminAreaList(result.get(0).getCompany_name());
					
					result.get(0).setArea_code(area_list);
					
					HMessage.setCompanydata(result);
					
					HMessage.setSitedata(SiteResult);
					
				}
			}

		
		return new ResponseEntity<>(HMessage, headers, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Message> loadingCustomerData() throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<CompanyDTO> result = companyDAO.loadingCustomerData();
		
		if(result.size() > 0) {
			
			message.setResult("success");
			message.setMessage("조회 성공");
			message.setStatus(StatusEnum.OK);
			message.setData(result);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}else {
			
			message.setResult("fail");
			message.setMessage("조회 실패 데이터가 없습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<Message> updateCustomerData(CompanyDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			if(dto.getCompany_name_kr() == null || dto.getCompany_name() == null || dto.getDomain_name() == null) {
				
				message.setResult("success");
				message.setMessage("데이터가 업데이트 실패하였습니다.");
				message.setStatus(StatusEnum.OK);
				message.setData(null);
				message.setError_code(ErrorEnum.NONE);
				
				return new ResponseEntity<>(message, headers, HttpStatus.OK);
			}
			
			companyDAO.updateCustomerData(dto);
			
			message.setResult("success");
			message.setMessage("데이터가 업데이트 되었습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			message.setResult("success");
			message.setMessage("데이터가 업데이트 실패하였습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		
	}
	
	@Override
	public ResponseEntity<Message> createNewCustomer(CompanyDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		UserDTO userdto = new UserDTO();
		
		Integer numtest = userDAO.OverlapID(dto.getCompany_admin());
		
		if(numtest > 0) {
			
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setMessage("생성 실패 - 유저 이메일 중복");
			message.setResult("fail");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
		
		else {
			
			companyDAO.createNewCustomer(dto);
			
			Date time = new Date();
			
			Calendar cal = Calendar.getInstance();
			
			Calendar cal1 = Calendar.getInstance();
			
			cal1.setTime(time);
			
			cal1.add(Calendar.HOUR, 9);
			
			cal.setTime(time);
			
			cal.add(Calendar.HOUR, 9);
			cal.add(Calendar.YEAR, 5);
			
			SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
			
			String Today = format1.format(cal1.getTime());
			
			String fullname = dto.getCompany_admin();
			
			String id = null;
			
			if(fullname.contains("@")) {
				
				StringBuffer str = new StringBuffer(fullname);
				
				int len = fullname.indexOf('@');
				
				StringBuffer domain = str.delete(len, fullname.length());
				
				id = domain.toString();
			}
			
			String pw = id+"_"+Today;
			
			String bcryPwd = passwordEncoder.encode(pw);
			
			userdto.setUser_id(dto.getCompany_admin());
			userdto.setUser_pw(bcryPwd);
			userdto.setCompany_name(dto.getCompany_name());
			userdto.setExpiration_datetime(cal.getTime());
			
			userDAO.RegisterAdminUser(userdto);
			
			message.setResult("success");
			message.setMessage("생성 성공");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData("none");
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}
	}

	@Override
	public ResponseEntity<Message> checkCompanyAdmin(CompanyDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		Integer numtest = userDAO.OverlapID(dto.getCompany_admin());
		
		if(numtest > 0) {
			
			message.setResult("fail");
			message.setMessage("이미 사용중입니다.");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			message.setData("none");
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		else { 
			
			message.setResult("success");
			message.setMessage("사용 가능 합니다.");
			message.setStatus(StatusEnum.OK);
			message.setError_code(ErrorEnum.NONE);
			message.setData("none");
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
	}
	
	public ResponseEntity<Message> createNewAreaInfo(String company_admin, 
			String construction_name, String area_code, int area_type, String area_address, String construction_detail,
			MultipartFile path_img, String owner_name, String owner_contact) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		

		try {

			String CompanyName = companyDAO.CompanyNameFind(company_admin);
			
			AdminAreaDTO areaDTO = new AdminAreaDTO();

			String path = "http://13.209.48.128:8080/areaImage/";
			
			String fullpath = path+path_img.getOriginalFilename();
			
			String savepath = "/home/ubuntu/files/area_image/"+path_img.getOriginalFilename();
			
			File savFile = new File(savepath);
			
			path_img.transferTo(savFile);
			
			areaDTO.setCompany_admin(company_admin);
			areaDTO.setCompany_name(CompanyName);
			areaDTO.setConstruction_name(construction_name);
			areaDTO.setArea_code(area_code);
			areaDTO.setArea_type(area_type);
			areaDTO.setArea_address(area_address);
			areaDTO.setConstruction_detail(construction_detail);
			areaDTO.setPath_img(fullpath);
			areaDTO.setOwner_name(owner_name);
			areaDTO.setOwner_contact(owner_contact);
			
			assignmentDAO.createNewAreaInfo(areaDTO);
			
			Date time = new Date();
			
			Calendar cal = Calendar.getInstance();
			
			cal.setTime(time);
			
			cal.add(Calendar.HOUR, 9);
			cal.add(Calendar.YEAR, 5);
			
			AssignmentDTO assgindto = new AssignmentDTO();
			
			assgindto.setUser_id(company_admin);
			assgindto.setArea_code(area_code);
			assgindto.setPath_img(fullpath);
			assgindto.setUser_type(1);
			assgindto.setExpiration_datetime(cal.getTime());
			
			assignmentDAO.Registerassginment(assgindto);
			
			message.setResult("success");
			message.setMessage("등록 성공");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setMessage("등록 실패");
			message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<Message> loadingAreaInfo(AreaDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		List<AreaDTO> result = assignmentDAO.loadingAreaInfo(dto);
		
		List<HomeDataRetrunDTO> TurnResult = new ArrayList<>();
		
		
		if(result.size() > 0) {
		
			for(int i = 0; i < result.size(); i++) {
				
				HomeDataRetrunDTO homedto = new HomeDataRetrunDTO();
				
				homedto.setCompany_name(result.get(i).getCompany_name());
				homedto.setOwner_name(result.get(i).getOwner_name());
				homedto.setArea_code(result.get(i).getArea_code());
				homedto.setConstruction_name(result.get(i).getConstruction_name());
				homedto.setArea_type(result.get(i).getArea_type());
				homedto.setConstruction_detail(result.get(i).getConstruction_detail());
				homedto.setArea_address(result.get(i).getArea_address());
				homedto.setOwner_contact(result.get(i).getOwner_contact());
				homedto.setSupervisor_name(result.get(i).getSupervisor_name());
				homedto.setSupervisor_contact(result.get(i).getSupervisor_contact());
				homedto.setContractor_name(result.get(i).getContractor_name());
				homedto.setContractor_contact(result.get(i).getContractor_contact());
				homedto.setPeriod_start(result.get(i).getPeriod_start());
				homedto.setPeriod_end(result.get(i).getPeriod_end());
				homedto.setStatus(result.get(i).getStatus());
				homedto.setPath_img(result.get(i).getPath_img());
				homedto.setCreation_datetime(result.get(i).getCreation_datetime());
				
				if(result.get(i).getFacility_type() == null) {
					
					System.out.println("null ok");
					
					Integer[] Arr = new Integer[1]; 
					
					Arr[0] = -9999;
					
					homedto.setFacility_type(Arr);
	
				}
				else if(result.get(i).getFacility_type().contains(",")){
					
					String str = result.get(i).getFacility_type();
					
					String[] strArr = str.split(",");
					
					Integer[] newArr = new Integer[strArr.length];
					
					for(int j = 0; j < newArr.length; j++) {
						newArr[j] = Integer.parseInt(strArr[j]);
					}
					
					homedto.setFacility_type(newArr);
				}

				else {
					
					Integer[] newArr = {Integer.parseInt(result.get(i).getFacility_type())};
					
					homedto.setFacility_type(newArr);
				}
				
				TurnResult.add(homedto);
			}
			
			
			message.setResult("success");
			message.setMessage("조회 성공");
			message.setStatus(StatusEnum.OK);
			message.setData(TurnResult);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}else {
			
			message.setResult("fail");
			message.setMessage("조회실패 - 데이터가 없습니다.");
			message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<Message> checkConstructionName(AreaDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		int result = assignmentDAO.checkConstructionName(dto);
		
		if(result > 0) {
			
			message.setResult("fail");
			message.setMessage("이미 사용중입니다.");
			message.setStatus(StatusEnum.INTERNAL_SERVER_ERROR);
			message.setData(null);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		else {
			
			message.setResult("success");
			message.setMessage("사용 가능합니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(result);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
	}
	
	
	@Override
	public ResponseEntity<Message> updateAreaImage(MultipartFile path_img, String area_code) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {

			AreaDTO areadto = new AreaDTO();
			
			String path = "http://13.209.48.128:8080/areaImage/";
			
			String fullpath = path+path_img.getOriginalFilename();
			
			String savepath = "/home/ubuntu/files/area_image/"+path_img.getOriginalFilename();
			
			File savFile = new File(savepath);
			
			path_img.transferTo(savFile);
			
			areadto.setArea_code(area_code);
			areadto.setPath_img(fullpath);
			
			assignmentDAO.updateAreaImage(areadto);
			assignmentDAO.updateAssginImage(areadto);
			
			message.setResult("success");
			message.setMessage("이미지 수정 완료하였습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			message.setResult("faul");
			message.setMessage("이미지 업데이트 실패하였습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		

		
		
	}
	
	@Override
	public ResponseEntity<Message> updateAreaInfo(HomeDataRetrunDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		
			
			
			
			
			AreaDTO inputdto = new AreaDTO();
			
			inputdto.setArea_code(dto.getArea_code());
			inputdto.setConstruction_detail(dto.getConstruction_detail());
			inputdto.setOwner_name(dto.getOwner_name());
			inputdto.setOwner_contact(dto.getOwner_contact());
			inputdto.setSupervisor_name(dto.getSupervisor_name());
			inputdto.setSupervisor_contact(dto.getSupervisor_contact());
			inputdto.setContractor_name(dto.getContractor_name());
			inputdto.setContractor_contact(dto.getContractor_contact());
			inputdto.setPeriod_start(dto.getPeriod_start());
			inputdto.setPeriod_end(dto.getPeriod_end());
			inputdto.setStatus(dto.getStatus());
			
			int[] intarr = Arrays.stream(dto.getFacility_type()).mapToInt(i->i).toArray();
			
			String fac = Arrays.stream(intarr)
	                .mapToObj(String::valueOf)
	                .reduce((x, y) -> x + "," + y)
	                .get();
					
			inputdto.setFacility_type(fac);
			
		
			assignmentDAO.updateAreaInfo(inputdto);
				
			message.setResult("success");
			message.setMessage("데이터가 업데이트 되었습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
				
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
				
		
		
	}
	
	@Override
	public ResponseEntity<UserMessage> MoveloadingUserData(AreaDTO dto) throws Exception {
		
		UserMessage message = new UserMessage();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		String user_id = dto.getUser_id();
		
		StringBuffer domain = null;
		
		List<AssignListDTO> result2 = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(user_id.contains("@")) {
			
			StringBuffer str = new StringBuffer(user_id);
			
			int len = user_id.indexOf('@');
			
			domain = str.delete(0, len);
		}
		
		//회사 도메인
		if(domain.toString().equals("@movements.kr")) {

			result2 = assignmentDAO.MoveloadingUserData2(dto);
			
		}else {
			
			message.setResult("fail");
			message.setMessage("무브먼츠 계정이 아닙니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			message.setUsers(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		
		List<AreaDTO> result1 = assignmentDAO.MoveloadingUserData(dto);

		
		
		if(result1.size() < 0|| result2.size() < 0) {
			
			message.setResult("fail");
			message.setMessage("해당 정보를 조회 할 수 없습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			message.setUsers(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}else {
			
			map.put("company_admin_Id", result1.get(0).getCompany_admin());
			map.put("area_address", result1.get(0).getArea_address());
			map.put("area_code", result1.get(0).getArea_code());
			map.put("creation_datetime", result1.get(0).getCreation_datetime());
			map.put("area_type", result1.get(0).getArea_type());
			map.put("construction_name", result1.get(0).getConstruction_name());
			
			message.setResult("success");
			message.setMessage("조회 성공");
			message.setStatus(StatusEnum.OK);
			message.setData(map);
			message.setError_code(ErrorEnum.NONE);
			message.setUsers(result2);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<UserMessage> loadingUserData(AreaDTO dto) throws Exception {
		
		UserMessage message = new UserMessage();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		String user_id = dto.getUser_id();
		
		AreaDTO adto = new AreaDTO();
		
		StringBuffer domain = null;
		
		List<AssignListDTO> result2 = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(user_id.contains("@")) {
			
			StringBuffer str = new StringBuffer(user_id);
			
			int len = user_id.indexOf('@');
			
			domain = str.delete(0, len);
		}
		
		//회사 도메인
		if(domain.toString().equals("@movements.kr")) {
			
			result2 = assignmentDAO.loadingUserData3(dto);
			
		}else {
			
			adto.setUser_id(domain.toString());
			adto.setArea_code(dto.getArea_code());
			
			result2 = assignmentDAO.loadingUserData2(adto);
		}
		
		List<AreaDTO> result1 = assignmentDAO.loadingUserData1(dto);
		
		if(result1.size() < 0|| result2.size() < 0) {
			
			message.setResult("fail");
			message.setMessage("해당 정보를 조회 할 수 없습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			message.setUsers(null);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		}

		else {
			
			map.put("company_admin_Id", result1.get(0).getCompany_admin());
			map.put("area_address", result1.get(0).getArea_address());
			map.put("area_code", result1.get(0).getArea_code());
			map.put("creation_datetime", result1.get(0).getCreation_datetime());
			map.put("area_type", result1.get(0).getArea_type());
			map.put("construction_name", result1.get(0).getConstruction_name());
			
			message.setResult("success");
			message.setMessage("조회 성공");
			message.setStatus(StatusEnum.OK);
			message.setData(map);
			message.setError_code(ErrorEnum.NONE);
			message.setUsers(result2);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		
	}
	
	@Override
	public ResponseEntity<Message> chageUserType(AssignmentDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			assignmentDAO.chageUserType(dto);
			
			message.setResult("success");
			message.setMessage("권한 변경에 성공하였습니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setMessage("권한 변경에 실패 하였습니다.");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setData(null);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<Message> unassignUser(AssignmentDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {
			
			assignmentDAO.unassignUser(dto);
			
			message.setResult("success");
			message.setMessage("할당 해제 성공");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
			
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setMessage("할당 해제 실패");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Message> searchUser(UserSendDTO dto) throws Exception {
	
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		StringBuffer domain = null;
		
		List<UserDTO> user_id = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(dto.getTarget_user_id().contains("@")) {
			
			StringBuffer str = new StringBuffer(dto.getTarget_user_id());
			
			int len = dto.getTarget_user_id().indexOf('@');
			
			domain = str.delete(0, len);
			
		}
		
		if(domain.toString().equals("@movements.kr")) {
			
			UserSendDTO udto = new UserSendDTO();
			
			udto.setUser_id(dto.getUser_id());
			
			//무브 경우
			user_id = userDAO.searchUser(udto);
			
			if(user_id.size() > 0) {
				
				map.put("user_id", user_id.get(0).user_id);
				
				message.setResult("success");
				message.setMessage("유저 조회 완료");
				message.setStatus(StatusEnum.OK);
				message.setData(map);
				message.setError_code(ErrorEnum.NONE);

				return new ResponseEntity<>(message, headers, HttpStatus.OK);
				
			}else {
				
				message.setResult("fail");
				message.setMessage("등록된 사용자가 없습니다.");
				message.setStatus(StatusEnum.BAD_REQUEST);
				message.setData(null);
				message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);

				return new ResponseEntity<>(message, headers, HttpStatus.OK);	
			}

				
		}else {
	
			UserSendDTO udto = new UserSendDTO();
			
			udto.setUser_id(dto.getUser_id());
			udto.setTarget_user_id(domain.toString());
			
			//다른 회사일 경우
			user_id = userDAO.searchUser2(udto);
			
			if(user_id.size() > 0) {
				
				map.put("user_id", user_id.get(0).user_id);
				
				message.setResult("success");
				message.setMessage("유저 조회 완료");
				message.setStatus(StatusEnum.OK);
				message.setData(map);
				message.setError_code(ErrorEnum.NONE);

				return new ResponseEntity<>(message, headers, HttpStatus.OK);
				
			}else {
				
				message.setResult("fail");
				message.setMessage("등록된 사용자가 없습니다.");
				message.setStatus(StatusEnum.BAD_REQUEST);
				message.setData(null);
				message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);

				return new ResponseEntity<>(message, headers, HttpStatus.OK);	
			}
		}

	}
	
	@Override
	public ResponseEntity<Message> assignUser(AssginUserDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		try {

			for (int i = 0; i < dto.getUsers().size(); i++) {

				AssginUserDTO assgindto = new AssginUserDTO();
				
				AssignmentDTO inputdto = new AssignmentDTO();
				
				TimeDTO timedto = new TimeDTO();
					
				timedto.setUser_id(dto.getCompany_admin());
				timedto.setArea_code(dto.getArea_code());
				
				assgindto.setCompany_admin(dto.getCompany_admin());
				assgindto.setArea_code(dto.getArea_code());
				
				Date expiration_datetime = assignmentDAO.FindExTime(timedto);
				
				String path_img = assignmentDAO.FindPathImg(dto.getArea_code());
				
				inputdto.setUser_id(dto.getUsers().get(i).getUser_id());
				inputdto.setArea_code(dto.getArea_code());
				inputdto.setUser_type(dto.getUsers().get(i).getUser_type());
				inputdto.setPath_img(path_img);
				inputdto.setExpiration_datetime(expiration_datetime);
				
				assignmentDAO.Registerassginment(inputdto);
				
			}
			
			message.setResult("success");
			message.setMessage("유저 추가 완료");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		
		} catch(Exception e) {
			
			message.setResult("fail");
			message.setMessage("계정 추가에 실패 하였습니다.");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setData(null);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		
		}

	}
	
	@Override
	public ResponseEntity<Message> checkAreaCode(AssginUserDTO dto) throws Exception {
		
		Message message = new Message();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		int result = assignmentDAO.checkAreaCode(dto.getArea_code());
		
		//중복
		if(result > 0) {
			
			message.setResult("fail");
			message.setMessage("이미 사용중입니다.");
			message.setStatus(StatusEnum.BAD_REQUEST);
			message.setData(null);
			message.setError_code(ErrorEnum.INTERNAL_SERVER_ERROR);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		else {
			
			message.setResult("success");
			message.setMessage("사용 가능 합니다.");
			message.setStatus(StatusEnum.OK);
			message.setData(null);
			message.setError_code(ErrorEnum.NONE);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}
		
	}

}
