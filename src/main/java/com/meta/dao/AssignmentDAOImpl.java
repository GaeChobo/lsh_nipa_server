package com.meta.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meta.dto.AdminAreaDTO;
import com.meta.dto.AreaDTO;
import com.meta.dto.AssginUserDTO;
import com.meta.dto.AssignListDTO;
import com.meta.dto.AssignmentDTO;
import com.meta.dto.TimeDTO;

public class AssignmentDAOImpl implements AssignmentDAO {

	@Autowired
	@Qualifier(value = "OneSqlSession")
	private SqlSession sqlsession;
	
	public static final String namespace = "mappers.assignmentMapper";
	
	@Override
	public List<AreaDTO> MoveloadingUserData(AreaDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".MoveloadingUserData", dto);
	}
	
	@Override
	public List<AssignListDTO> MoveloadingUserData2(AreaDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".MoveloadingUserData2", dto);
	}
	
	@Override
	public List<AssignListDTO> loadingUserData3(AreaDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".loadingUserData3", dto);
	}
	
	@Override
	public List<String> OwenerSelectione() throws Exception {
		return sqlsession.selectList(namespace+".OwenerSelectione");
	}
	
	@Override
	public Integer AdminCompanyFind(String user_id) throws Exception {
		return sqlsession.selectOne(user_id);
	}
	
	@Override
	public int checkAreaCode(String area_code) throws Exception {
		return sqlsession.selectOne(area_code);
	}
	
	@Override
	public Date FindExTime(TimeDTO dto) throws Exception {
		return sqlsession.selectOne(namespace+".FindExTime", dto);
	}
	
	@Override
	public String FindPathImg(String area_code) throws Exception {
		return sqlsession.selectOne(namespace+".FindPathImg", area_code);
	}
	
	@Override
	public void unassignUser(AssignmentDTO dto) throws Exception {
		sqlsession.delete(namespace+".unassignUser", dto);
	}
	
	@Override
	public void chageUserType(AssignmentDTO dto) throws Exception {
		sqlsession.update(namespace+".chageUserType", dto);
	}
	
	@Override
	public List<AreaDTO> CompanyNameFindList(AreaDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".CompanyNameFindList", dto);
	}
	
	@Override
	public List<AreaDTO> loadingUserData1(AreaDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".loadingUserData1", dto);
	}
	
	@Override
	public List<AssignListDTO> loadingUserData2(AreaDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".loadingUserData2", dto);
	}
	
	@Override
	public void updateAssginImage(AreaDTO dto) throws Exception {
		sqlsession.update(namespace+".updateAssginImage", dto);
	}
	
	@Override
	public void updateAreaImage(AreaDTO dto) throws Exception {
		sqlsession.update(namespace+".updateAreaImage", dto);
	}
	
	@Override
	public void updateAreaInfo(AreaDTO dto) throws Exception {
		sqlsession.update(namespace+".updateAreaInfo", dto);
	}
	
	@Override
	public int checkConstructionName(AreaDTO dto) throws Exception {
		return sqlsession.selectOne(namespace+".checkConstructionName", dto);
	}
	
	@Override
	public List<AreaDTO> loadingAreaInfo(AreaDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".loadingAreaInfo", dto);
	}
	
	@Override
	public void createNewAreaInfo(AdminAreaDTO dto) throws Exception {
		sqlsession.insert(namespace+".createNewAreaInfo", dto);
	}
	
	@Override
	public List<String> AdminAreaList(String company_name) throws Exception {
		return sqlsession.selectList(namespace+".AdminAreaList", company_name);
	}
	
	@Override
	public List<AreaDTO> AdminAreaInfo(AreaDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".AdminAreaInfo", dto);
	}
	
	@Override
	public List<String> OwenerList(AreaDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".OwenerList", dto);
	}
	
	@Override
	public List<AssignListDTO> AssignUserList(AssignListDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".AssignUserList", dto);
	}
	
	@Override
	public void Registerassginment (AssignmentDTO dto) throws Exception {
		sqlsession.insert(namespace+".Registerassginment", dto);
	}
	
	@Override
	public List<AssignmentDTO> Listassignment(String user_id )throws Exception {
		return sqlsession.selectList(user_id);
	}
	
	@Override
	public List<AssignmentDTO> SelectionComplete(String user_id, String area_code)throws Exception {
		return sqlsession.selectList(user_id, area_code);
	}
	
	@Override
	public List<AreaDTO> assignmentAreaDetail(String area_code)throws Exception {
		return sqlsession.selectList(area_code);
	}
}
