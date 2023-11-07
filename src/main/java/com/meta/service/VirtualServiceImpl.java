package com.meta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.dao.VirtualDAO;
import com.meta.dto.VirtualDTO;
import com.meta.dto.VirtualFinalDTO;
import com.meta.dto.VirtualLocationDTO;

@Service
public class VirtualServiceImpl implements VirtualService {

	@Autowired
	VirtualDAO dao;
	
	public void RegisterVirtualDetail(VirtualDTO dto) throws Exception {
		dao.RegisterVirtualDetail(dto);
	}
	
	public void UpdateVirtualDetail(VirtualDTO dto) throws Exception {
		dao.UpdateVirtualDetail(dto);
	}
	
	public void RegisterVirtualLocation(VirtualLocationDTO dto) throws Exception {
		dao.RegisterVirtualLocation(dto);
	}

	public String DownloadLinkVirtaulCSV(String version_virtual) throws Exception {
		return dao.DownloadLinkVirtaulCSV(version_virtual);
	}

	public void RegisterFinalVirtual(VirtualFinalDTO dto) throws Exception {
		dao.RegisterFinalVirtual(dto);
	}

	public void DeleteFinalVirtual(VirtualFinalDTO dto) throws Exception {
		dao.DeleteFinalVirtual(dto);
	}
}
