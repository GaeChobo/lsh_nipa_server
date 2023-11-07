package com.meta.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.meta.dto.AdminAreaDTO;
import com.meta.dto.AreaDTO;
import com.meta.dto.AssginUserDTO;
import com.meta.dto.AssignmentDTO;
import com.meta.dto.CompanyDTO;
import com.meta.dto.HomeDataRetrunDTO;
import com.meta.dto.UserDTO;
import com.meta.dto.UserSendDTO;
import com.meta.vo.HomeMessage;
import com.meta.vo.Message;
import com.meta.vo.UserMessage;

public interface AdminService {
	
	public ResponseEntity<UserMessage> MoveloadingUserData(AreaDTO dto) throws Exception;
	
	public ResponseEntity<Message> OwenerSelectione() throws Exception;
	
	public ResponseEntity<Message> checkAreaCode(AssginUserDTO dto) throws Exception;
	
	public ResponseEntity<Message> assignUser(AssginUserDTO dto) throws Exception;
	
	public ResponseEntity<Message> searchUser(UserSendDTO dto) throws Exception;
	
	public ResponseEntity<Message> unassignUser(AssignmentDTO dto) throws Exception;
	
	public ResponseEntity<Message> chageUserType(AssignmentDTO dto) throws Exception;
	
	public ResponseEntity<UserMessage> loadingUserData(AreaDTO dto) throws Exception;
	
	public ResponseEntity<Message> updateAreaImage(MultipartFile path_img, String area_code) throws Exception;
	
	public ResponseEntity<Message> updateAreaInfo(HomeDataRetrunDTO dto) throws Exception;
	
	public ResponseEntity<Message> checkConstructionName(AreaDTO dto) throws Exception;
	
	public ResponseEntity<Message> loadingAreaInfo(AreaDTO dto) throws Exception;
	
	public ResponseEntity<Message> createNewAreaInfo(String company_admin, 
			String construction_name, String area_code, int area_type, String area_address, String construction_detail,
			MultipartFile path_img, String owner_name, String owner_contact) throws Exception;
	
	public ResponseEntity<Message> checkCompanyAdmin(CompanyDTO dto) throws Exception;
	
	public ResponseEntity<Message> createNewCustomer(CompanyDTO dto) throws Exception;
	
	public ResponseEntity<HomeMessage> loadingHomeData(String user_id) throws Exception;
	
	public ResponseEntity<Message> loadingCustomerData() throws Exception;
	
	public ResponseEntity<Message> updateCustomerData(CompanyDTO dto) throws Exception;

}
