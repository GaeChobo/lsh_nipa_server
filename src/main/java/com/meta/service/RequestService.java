package com.meta.service;

import java.util.List;

import com.meta.dto.Interference_detailDTO;
import com.meta.dto.Interference_locationDTO;
import com.meta.dto.Request_preprocessingDTO;
import com.meta.dto.Request_resultDTO;
import com.meta.dto.VirtualLocationDTO;
import com.meta.dto.Virtual_detailDTO;

public interface RequestService {

	public List<VirtualLocationDTO> VirtualDetailLocation(String version_virtual) throws Exception;
	
	public List<Request_resultDTO> VirtualDetailResult(Virtual_detailDTO dto) throws Exception;
	
	public List<Interference_locationDTO> interferenceDetailLocation(String version_interference) throws Exception;
	
	public List<Request_resultDTO> interferenceDetailResult(Interference_detailDTO dto) throws Exception;
	
	public void DeleteRequestUpdate(Request_preprocessingDTO dto) throws Exception;
	
	public List<Request_resultDTO> RequestCardList(Request_preprocessingDTO dto) throws Exception;
	
	public int CheckRequestVersion(String version_drawing) throws Exception;
	
	public void RequestDrawing(Request_preprocessingDTO dto) throws Exception;
	
	public void UpdateAdminCheck(Request_preprocessingDTO dto) throws Exception;
	
	public void UpdateDrawingComplete(Request_preprocessingDTO dto) throws Exception;
	
	public int ExistRequestVersion(String version_drawing) throws Exception;
	
	public void UpdateCustomerCheck(Request_preprocessingDTO dto) throws Exception;
	
	public List<Request_preprocessingDTO> DrawingUserSelect(Request_preprocessingDTO dto) throws Exception;
	
	public int UserRequestNum(Request_preprocessingDTO dto) throws Exception;
	
	public void RequestinterferenceComplete(Request_preprocessingDTO dto) throws Exception;
	
	public void RequestVirtualComplete(Request_preprocessingDTO dto) throws Exception;
}
