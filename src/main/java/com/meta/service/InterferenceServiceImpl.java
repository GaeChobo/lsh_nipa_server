package com.meta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.dao.InterferenceDAO;
import com.meta.dto.InterferenceDTO;
import com.meta.dto.InterferenceLocationDTO;
import com.meta.dto.Interference_detailDTO;
import com.meta.dto.Interference_final_drawingDTO;

@Service
public class InterferenceServiceImpl implements InterferenceService {

	@Autowired
	InterferenceDAO dao;
	
	@Override
	public void DeleteFinalInterference(Interference_final_drawingDTO dto) throws Exception {
		dao.DeleteFinalInterference(dto);
	}
	
	@Override
	public void RegisterFinalInterference(Interference_final_drawingDTO dto) throws Exception {
		dao.RegisterFinalInterference(dto);
	}
	
	@Override
	public void RegisterInterferenceDetail(InterferenceDTO dto) throws Exception {
		dao.RegisterInterferenceDetail(dto);
	}
	
	@Override
	public void UpdateInterferenceDetail(Interference_detailDTO dto) throws Exception {
		dao.UpdateInterferenceDetail(dto);
	}
	
	@Override
	public void RegisterInterferenceLocation(InterferenceLocationDTO dto) throws Exception {
		dao.RegisterInterferenceLocation(dto);
	}
	
	@Override
	public String FindVersionRequest(String version_interference) throws Exception {
		return dao.FindVersionRequest(version_interference);
	}
	
	@Override
	public String DownloadLinkInterferenceCSV(Interference_detailDTO dto) throws Exception {
		return dao.DownloadLinkInterferenceCSV(dto);
	}
	
	@Override
	public List<InterferenceDTO> interferenceChoiceList(InterferenceDTO dto) throws Exception {
		return dao.interferenceChoiceList(dto);
	}
}
