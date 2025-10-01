package com.ms.companyms.company.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ms.companyms.company.Company;
import com.ms.companyms.company.CompanyRepository;
import com.ms.companyms.company.CompanyService;
import com.ms.companyms.company.dto.ReviewMessage;

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
			
			companyRepository.save(companyToUpdate);
			return true;
		}
		return false;
	}

	@Override
	public void updateCompanyRating(ReviewMessage reviewMessage) {
		
	}

}
