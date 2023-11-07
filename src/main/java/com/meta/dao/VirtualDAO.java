package com.meta.dao;

import com.meta.dto.VirtualDTO;
import com.meta.dto.VirtualFinalDTO;
import com.meta.dto.VirtualLocationDTO;

public interface VirtualDAO {

	public void RegisterVirtualDetail(VirtualDTO dto) throws Exception;
	
	public void UpdateVirtualDetail(VirtualDTO dto) throws Exception;
	
	public void RegisterVirtualLocation(VirtualLocationDTO dto) throws Exception;

	public String DownloadLinkVirtaulCSV(String version_virtual) throws Exception;

	public void RegisterFinalVirtual(VirtualFinalDTO dto) throws Exception;

	public void DeleteFinalVirtual(VirtualFinalDTO dto) throws Exception;
}
