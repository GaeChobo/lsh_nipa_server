package com.meta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.dao.RequestDAO;
import com.meta.dto.Interference_detailDTO;
import com.meta.dto.Interference_locationDTO;
import com.meta.dto.Request_preprocessingDTO;
import com.meta.dto.Request_resultDTO;
import com.meta.dto.VirtualLocationDTO;
import com.meta.dto.Virtual_detailDTO;

@Service
public class RequestServiceImpl implements RequestService {

	@Autowired
	RequestDAO dao;
	
	@Override
	public List<VirtualLocationDTO> VirtualDetailLocation(String version_virtual) throws Exception {
		return dao.VirtualDetailLocation(version_virtual);
	}
	
	@Override
	public List<Request_resultDTO> VirtualDetailResult(Virtual_detailDTO dto) throws Exception {
		return dao.VirtualDetailResult(dto);
	}
	
	@Override
	public List<Interference_locationDTO> interferenceDetailLocation(String version_interference) throws Exception {
		return dao.interferenceDetailLocation(version_interference);
	}
	
	@Override
	public List<Request_resultDTO> interferenceDetailResult(Interference_detailDTO dto) throws Exception {
		return dao.interferenceDetailResult(dto);
	}
	
	@Override
	public void DeleteRequestUpdate(Request_preprocessingDTO dto) throws Exception {
		dao.DeleteRequestUpdate(dto);
	}
	
	@Override
	public List<Request_resultDTO> RequestCardList(Request_preprocessingDTO dto) throws Exception {
		return dao.RequestCardList(dto);
	}
	
	@Override
	public int CheckRequestVersion(String version_drawing) throws Exception {
		return dao.CheckRequestVersion(version_drawing);
	}
	
	@Override
	public void RequestinterferenceComplete(Request_preprocessingDTO dto) throws Exception {
		dao.RequestinterferenceComplete(dto);
	}
	
	@Override
	public int UserRequestNum(Request_preprocessingDTO dto) throws Exception {
		return dao.UserRequestNum(dto);
	}
	
	@Override
	public List<Request_preprocessingDTO> DrawingUserSelect(Request_preprocessingDTO dto) throws Exception {
		return dao.DrawingUserSelect(dto);
	}
	
	@Override
	public void UpdateCustomerCheck(Request_preprocessingDTO dto) throws Exception {
		dao.UpdateCustomerCheck(dto);
	}
	
	@Override
	public void RequestDrawing(Request_preprocessingDTO dto) throws Exception {
		dao.RequestDrawing(dto);
	}
	
	@Override
	public void UpdateAdminCheck(Request_preprocessingDTO dto) throws Exception {
		dao.UpdateAdminCheck(dto);
	}
	
	@Override
	public void UpdateDrawingComplete(Request_preprocessingDTO dto) throws Exception {
		dao.UpdateDrawingComplete(dto);
	}
	
	@Override
	public int ExistRequestVersion(String version_drawing) throws Exception {
		int result = dao.ExistRequestVersion(version_drawing);
		return result;
	}
	
	@Override
	public void RequestVirtualComplete(Request_preprocessingDTO dto) throws Exception {
		dao.RequestVirtualComplete(dto);
	}
}
