package com.ms.jobms.job;

import java.util.List;

import com.ms.jobms.job.dto.JobDTO;


public interface JobService {
	public List<JobDTO> findAll();
	public void createJob( Job job);
	public JobDTO getJobById(Long id);
	public boolean deleteJobById(Long id);
	public boolean updateJob(Long id, Job job);
}
