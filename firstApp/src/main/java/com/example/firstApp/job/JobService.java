package com.example.firstApp.job;

import java.util.List;


public interface JobService {
	public List<Job> findAll();
	public void createJob( Job job);
	public Job getJobById(Long id);
	public boolean deleteJobById(Long id);
	public boolean updateJob(Long id, Job job);
}
