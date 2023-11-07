package com.meta.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meta.dto.AdminCompanyDTO;
import com.meta.dto.CompanyDTO;
import com.meta.dto.PCompanyDTO;

public class CompanyDAOImpl implements CompanyDAO {

	@Autowired
	@Qualifier(value = "OneSqlSession")
	private SqlSession sqlsession;
	
	public static final String namespace = "mappers.companyMapper";
	
	@Override
	public void createNewCustomer(CompanyDTO dto) throws Exception {
		
		sqlsession.insert(namespace+".createNewCustomer", dto);
	}
	
	@Override
	public void updateCustomerData(CompanyDTO dto) throws Exception {
		
		sqlsession.update(namespace+".updateCustomerData", dto);
	}
	
	@Override
	public List<CompanyDTO> loadingCustomerData() throws Exception {
		
		return sqlsession.selectList(namespace+".loadingCustomerData");
	}
	
	@Override
	public List<String> CompanyNameList() throws Exception {
		
		return sqlsession.selectList(namespace+".CompanyNameList");
	}
	
	@Override
	public List<AdminCompanyDTO> DomainList(AdminCompanyDTO dto) throws Exception {
		
		return sqlsession.selectList(namespace+".DomainList", dto);
	}
	
	@Override
	public List<CompanyDTO> CompanyDomainList() throws Exception {
		
		return sqlsession.selectList(namespace+".CompanyDomainList");
	}
	
	@Override
	public String OverlapDomain(String domain_name) throws Exception {
		
		return sqlsession.selectOne(domain_name);
	}
	
	@Override
	public void RegisterDomain(CompanyDTO dto) throws Exception {
		
		sqlsession.insert(namespace+".RegisterDomain", dto);
	}
	
	@Override
	public String CompanyNameFind(String company_admin) throws Exception {
		return sqlsession.selectOne(company_admin);
	}
	
}
