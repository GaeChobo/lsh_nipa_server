package com.meta.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meta.dto.Interference_detailDTO;
import com.meta.dto.Interference_locationDTO;
import com.meta.dto.Request_preprocessingDTO;
import com.meta.dto.Request_resultDTO;
import com.meta.dto.VirtualLocationDTO;
import com.meta.dto.Virtual_detailDTO;

public class RequestDAOImpl implements RequestDAO {

	@Autowired
	@Qualifier(value = "OneSqlSession")
	private SqlSession sqlsession;
	
	public static final String namespace = "mappers.requestMapper";
	
	@Override
	public List<VirtualLocationDTO> VirtualDetailLocation(String version_virtual) throws Exception {
		return sqlsession.selectList(namespace+".VirtualDetailLocation", version_virtual);
	}
	
	@Override
	public List<Request_resultDTO> VirtualDetailResult(Virtual_detailDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".VirtualDetailResult", dto);
	}
	
	@Override
	public List<Interference_locationDTO> interferenceDetailLocation(String version_interference) throws Exception {
		return sqlsession.selectList(namespace+".interferenceDetailLocation", version_interference);
	}
	
	@Override
	public List<Request_resultDTO> interferenceDetailResult(Interference_detailDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".interferenceDetailResult", dto);
	}
	
	@Override
	public void DeleteRequestUpdate(Request_preprocessingDTO dto) throws Exception {
		sqlsession.update(namespace+".DeleteRequestUpdate", dto);
	}
	
	@Override
	public List<Request_resultDTO> RequestCardList(Request_preprocessingDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".RequestCardList", dto);
	}
	
	@Override
	public int CheckRequestVersion(String version_drawing) throws Exception {
		return sqlsession.selectOne(namespace+".CheckRequestVersion", version_drawing);
	}
	
	@Override
	public void RequestinterferenceComplete(Request_preprocessingDTO dto) throws Exception {
		sqlsession.update(namespace+".RequestinterferenceComplete", dto);
	}
	
	@Override
	public int UserRequestNum(Request_preprocessingDTO dto) throws Exception {
		return sqlsession.selectOne(namespace+".UserRequestNum", dto);
	}
	
	@Override
	public List<Request_preprocessingDTO> DrawingUserSelect(Request_preprocessingDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".DrawingUserSelect", dto);
	}
	
	@Override
	public void UpdateCustomerCheck(Request_preprocessingDTO dto) throws Exception {
		sqlsession.update(namespace+".UpdateCustomerCheck", dto);
	}
	
	@Override
	public void RequestDrawing(Request_preprocessingDTO dto) throws Exception {
		sqlsession.insert(namespace+".RequestDrawing", dto);
	}
	
	@Override
	public void UpdateAdminCheck(Request_preprocessingDTO dto) throws Exception {
		sqlsession.update(namespace+".UpdateAdminCheck", dto);
	}
	
	@Override
	public void UpdateDrawingComplete(Request_preprocessingDTO dto) throws Exception {
		sqlsession.update(namespace+".UpdateDrawingComplete", dto);
	}
	
	@Override
	public int ExistRequestVersion(String version_drawing) throws Exception {
		int result = sqlsession.selectOne(version_drawing);
		return result;
	}
	
	@Override
	public void RequestVirtualComplete(Request_preprocessingDTO dto) throws Exception {
		sqlsession.update(namespace+".RequestVirtualComplete", dto);
	}
}
