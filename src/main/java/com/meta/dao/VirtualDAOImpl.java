package com.meta.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meta.dto.VirtualDTO;
import com.meta.dto.VirtualFinalDTO;
import com.meta.dto.VirtualLocationDTO;

public class VirtualDAOImpl implements VirtualDAO {

	@Autowired
	@Qualifier(value = "OneSqlSession")
	private SqlSession sqlsession;
	
	public static final String namespace = "mappers.virtaulMapper";
	
	@Override
	public void RegisterVirtualDetail(VirtualDTO dto) throws Exception {
		sqlsession.insert(namespace+".RegisterVirtualDetail", dto);
	}
	
	@Override
	public void UpdateVirtualDetail(VirtualDTO dto) throws Exception {
		sqlsession.update(namespace+".UpdateVirtualDetail", dto);
	}
	
	@Override
	public void RegisterVirtualLocation(VirtualLocationDTO dto) throws Exception {
		sqlsession.insert(namespace+".RegisterVirtualLocation", dto);
	}

	@Override
	public String DownloadLinkVirtaulCSV(String version_virtual) throws Exception {
		return sqlsession.selectOne(namespace+".DownloadLinkVirtaulCSV", version_virtual);
	}

	@Override
	public void RegisterFinalVirtual(VirtualFinalDTO dto) throws Exception {
		sqlsession.insert(namespace+".RegisterFinalVirtual", dto);
	}

	@Override
	public void DeleteFinalVirtual(VirtualFinalDTO dto) throws Exception {
		sqlsession.delete(namespace+".DeleteFinalVirtual", dto);
	}

}
