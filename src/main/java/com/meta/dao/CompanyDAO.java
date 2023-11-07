package com.meta.dao;

import java.util.List;

import com.meta.dto.AdminCompanyDTO;
import com.meta.dto.CompanyDTO;
import com.meta.dto.PCompanyDTO;

public interface CompanyDAO {

	public String CompanyNameFind(String company_admin) throws Exception;
	
	public void createNewCustomer(CompanyDTO dto) throws Exception;
	
	public void updateCustomerData(CompanyDTO dto) throws Exception;
	
	public List<CompanyDTO> loadingCustomerData() throws Exception;
	
	public List<String> CompanyNameList() throws Exception;
	
	public List<AdminCompanyDTO> DomainList(AdminCompanyDTO dto) throws Exception;
	
	public List<CompanyDTO> CompanyDomainList() throws Exception;
	
	public String OverlapDomain(String domain_name) throws Exception;
	
	public void RegisterDomain(CompanyDTO dto) throws Exception;
	
}
