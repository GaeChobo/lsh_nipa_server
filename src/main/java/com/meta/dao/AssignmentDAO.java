package com.meta.dao;

import java.util.Date;
import java.util.List;


import com.meta.dto.AdminAreaDTO;
import com.meta.dto.AreaDTO;
import com.meta.dto.AssginUserDTO;
import com.meta.dto.AssignListDTO;
import com.meta.dto.AssignmentDTO;
import com.meta.dto.PCompanyDTO;
import com.meta.dto.TimeDTO;

public interface AssignmentDAO {
	
	public List<String> OwenerSelectione() throws Exception;
	
	public Integer AdminCompanyFind(String user_id) throws Exception;
	
	public int checkAreaCode(String area_code) throws Exception;
	
	public Date FindExTime(TimeDTO dto) throws Exception;
	
	public String FindPathImg(String area_code) throws Exception;
	
	public void unassignUser(AssignmentDTO dto) throws Exception;
	
	public void chageUserType(AssignmentDTO dto) throws Exception;
	 
	public List<AreaDTO> CompanyNameFindList(AreaDTO dto) throws Exception;
	
	public List<AreaDTO> loadingUserData1(AreaDTO dto) throws Exception;
	
	public List<AssignListDTO> loadingUserData2(AreaDTO dto) throws Exception;
	
	public List<AssignListDTO> loadingUserData3(AreaDTO dto) throws Exception;
	
	public List<AreaDTO> MoveloadingUserData(AreaDTO dto) throws Exception;
	
	public List<AssignListDTO> MoveloadingUserData2(AreaDTO dto) throws Exception;
	
	public void updateAssginImage(AreaDTO dto) throws Exception;
	
	public void updateAreaImage(AreaDTO dto) throws Exception;
	
	public void updateAreaInfo(AreaDTO dto) throws Exception;
	
	public int checkConstructionName(AreaDTO dto) throws Exception;
	
	public List<AreaDTO> loadingAreaInfo(AreaDTO dto) throws Exception;
	
	public void createNewAreaInfo(AdminAreaDTO dto) throws Exception;
	
	public List<String> AdminAreaList(String company_name) throws Exception;
	
	public List<AreaDTO> AdminAreaInfo(AreaDTO dto) throws Exception;
	
	public List<String> OwenerList(AreaDTO dto) throws Exception;
	
	public List<AssignListDTO> AssignUserList(AssignListDTO dto) throws Exception;
	
	public void Registerassginment (AssignmentDTO dto) throws Exception;
	
	public List<AssignmentDTO> Listassignment(String user_id )throws Exception;
	
	public List<AssignmentDTO> SelectionComplete(String user_id, String area_code)throws Exception;
	
	public List<AreaDTO> assignmentAreaDetail(String area_code)throws Exception;
}
