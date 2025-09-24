package com.ms.jobms.job.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ms.jobms.job.Job;
import com.ms.jobms.job.JobRepository;
import com.ms.jobms.job.JobService;
import com.ms.jobms.job.dto.JobWithCompanyDTO;
import com.ms.jobms.job.external.Company;

@Service
public class JobServiceImpl implements JobService {

	JobRepository jobRepository;

	public JobServiceImpl(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	@Override
	public List<JobWithCompanyDTO> findAll() {
		List<Job> jobs = jobRepository.findAll();
		return jobs.stream().map(this::convertJobToDto).collect(Collectors.toList());
	}
	
	/**
	 * Converts a job to a job dto
	 * Fetches the company to attach to the job dto using RestTemplate
	 * @param job
	 * @return
	 */
	private JobWithCompanyDTO convertJobToDto(Job job) {
		JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
		jobWithCompanyDTO.setJob(job);
		RestTemplate restTemplate = new RestTemplate();
		Company company = restTemplate.getForObject("http://localhost:8081/companies/" + job.getCompanyId(), Company.class);
		jobWithCompanyDTO.setCompany(company);
		return jobWithCompanyDTO;
	}

	@Override
	public void createJob(Job job) {
		jobRepository.save(job);
	}

	@Override
	public Job getJobById(Long id) {
		return jobRepository.findById(id).orElse(null);
	}

	@Override
	public boolean deleteJobById(Long id) {
		try {
			jobRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateJob(Long id, Job updatedJob) {
		Optional<Job> jobOptional = jobRepository.findById(id);
		if (jobOptional.isPresent()) {
			Job job = jobOptional.get();
			job.setTitle(updatedJob.getTitle());
			job.setDescription(updatedJob.getDescription());
			job.setMinSalary(updatedJob.getMinSalary());
			job.setMaxSalary(updatedJob.getMaxSalary());
			job.setLocation(updatedJob.getLocation());
			jobRepository.save(job);
			return true;
		}
		return false;
	}

}
