package com.meta.dao;

import java.util.List;

import com.meta.dto.InterferenceDTO;
import com.meta.dto.InterferenceLocationDTO;
import com.meta.dto.Interference_detailDTO;
import com.meta.dto.Interference_final_drawingDTO;

public interface InterferenceDAO {

	public void RegisterInterferenceDetail(InterferenceDTO dto) throws Exception;
	
	public void UpdateInterferenceDetail(Interference_detailDTO dto) throws Exception;
	
	public void RegisterInterferenceLocation(InterferenceLocationDTO dto) throws Exception;
	
	public String FindVersionRequest(String version_interference) throws Exception;
	
	public String DownloadLinkInterferenceCSV(Interference_detailDTO dto) throws Exception;
	
	public void RegisterFinalInterference(Interference_final_drawingDTO dto) throws Exception;

	public void DeleteFinalInterference(Interference_final_drawingDTO dto) throws Exception;
	
	public List<InterferenceDTO> interferenceChoiceList(InterferenceDTO dto) throws Exception;
}
