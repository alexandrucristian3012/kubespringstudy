package com.example.firstApp.company.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.firstApp.company.Company;
import com.example.firstApp.company.CompanyRepository;
import com.example.firstApp.company.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	private CompanyRepository companyRepository;
	
	
	
	public CompanyServiceImpl(CompanyRepository companyRepository) {
		super();
		this.companyRepository = companyRepository;
	}

	@Override
	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	@Override
	public void createCompany(Company company) {
		companyRepository.save(company);
		
	}

	@Override
	public Company getCompanyById(Long id) {
		return companyRepository.findById(id).orElse(null);
	}

	@Override
	public boolean deleteCompanyById(Long id) {
		if(companyRepository.existsById(id)) {
			companyRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public boolean updateCompany(Long id, Company company) {
		Optional<Company> companyOptional = companyRepository.findById(id);
		if (companyOptional.isPresent()) {
			Company companyToUpdate = companyOptional.get();
			companyToUpdate.setName(company.getName());
			companyToUpdate.setDescription(company.getDescription());
			companyToUpdate.setJobs(company.getJobs());
			companyRepository.save(companyToUpdate);
			return true;
		}
		return false;
	}

}
