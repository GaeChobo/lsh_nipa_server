package com.meta.dao;

import java.util.List;

import com.meta.dto.CctvDTO;

public interface CctvDAO {

	public void CCTVAllDelete() throws Exception;
	
	public List<CctvDTO> CCTVAllList() throws Exception;
	
	public List<CctvDTO> AdminCctvSearch(CctvDTO dto) throws Exception;
	
	public List<CctvDTO> CCTV_NO1_Find(CctvDTO dto) throws Exception;

	public List<CctvDTO> CCTV_NO2_Find(CctvDTO dto) throws Exception;
	
	public List<CctvDTO> CCTV_NO3_Find(CctvDTO dto) throws Exception;
	
	public List<CctvDTO> CCTV_NO4_Find(CctvDTO dto) throws Exception;
	
	public void RegisterCCTV(CctvDTO dto) throws Exception;
	
	public String CCTVImageFindList(String cctv_no) throws Exception;
}
