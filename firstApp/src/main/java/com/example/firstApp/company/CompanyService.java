package com.example.firstApp.company;

import java.util.List;


public interface CompanyService {
	public List<Company> getAllCompanies();
	public void createCompany( Company company);
	public Company getCompanyById(Long id);
	public boolean deleteCompanyById(Long id);
	public boolean updateCompany(Long id, Company job);
}
