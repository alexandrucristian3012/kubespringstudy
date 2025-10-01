package com.ms.companyms.company;

import java.util.List;

import com.ms.companyms.company.dto.ReviewMessage;


public interface CompanyService {
	public List<Company> getAllCompanies();
	public void createCompany( Company company);
	public Company getCompanyById(Long id);
	public boolean deleteCompanyById(Long id);
	public boolean updateCompany(Long id, Company job);
	public void updateCompanyRating(ReviewMessage reviewMessage);
}
