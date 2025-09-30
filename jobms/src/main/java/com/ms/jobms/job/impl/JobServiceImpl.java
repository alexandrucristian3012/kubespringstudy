package com.ms.jobms.job.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ms.jobms.job.Job;
import com.ms.jobms.job.JobRepository;
import com.ms.jobms.job.JobService;
import com.ms.jobms.job.dto.JobDTO;
import com.ms.jobms.job.external.Company;
import com.ms.jobms.job.external.Review;
import com.ms.jobms.job.feignClients.CompanyClient;
import com.ms.jobms.job.feignClients.ReviewClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class JobServiceImpl implements JobService {

	JobRepository jobRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	private CompanyClient companyClient;
	private ReviewClient reviewClient;

	public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
		this.jobRepository = jobRepository;
		this.companyClient = companyClient;
		this.reviewClient = reviewClient;
	}

	@Override
	@CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
//	@Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
//	@RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
	public List<JobDTO> findAll() {
		List<Job> jobs = jobRepository.findAll();
		return jobs.stream().map(this::convertJobToDto).collect(Collectors.toList());
	}
	
	public List<String> companyBreakerFallback() {
		List<String> list = new ArrayList<>();
		list.add("Fallback");
		return list;
	}
	
	/**
	 * Converts a job to a job dto
	 * Fetches the company to attach to the job dto using RestTemplate
	 * @param job
	 * @return
	 */
	private JobDTO convertJobToDto(Job job) {
		JobDTO jobDTO = new JobDTO();
		jobDTO.setId(job.getId());
		jobDTO.setTitle(job.getTitle());
		jobDTO.setDescription(job.getDescription());
		jobDTO.setMinSalary(job.getMinSalary());
		jobDTO.setMaxSalary(job.getMaxSalary());
		jobDTO.setLocation(job.getLocation());
		Company company = companyClient.getCompany(job.getCompanyId());
		List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
		jobDTO.setReviews(reviews);
		
		jobDTO.setCompany(company);
		return jobDTO;
	}

	@Override
	public void createJob(Job job) {
		jobRepository.save(job);
	}

	@Override
	public JobDTO getJobById(Long id) {
		Job job = jobRepository.findById(id).orElse(null);
		return convertJobToDto(job);
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
