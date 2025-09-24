package com.ms.jobms.job;

import java.util.List;

import com.ms.jobms.job.dto.JobWithCompanyDTO;


public interface JobService {
	public List<JobWithCompanyDTO> findAll();
	public void createJob( Job job);
	public Job getJobById(Long id);
	public boolean deleteJobById(Long id);
	public boolean updateJob(Long id, Job job);
}
