package com.example.firstApp.job.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.firstApp.job.Job;
import com.example.firstApp.job.JobService;

@Service
public class JobServiceImpl implements JobService {

	private List<Job> jobs = new ArrayList<>();
	
	@Override
	public List<Job> findAll() {
		return jobs;
	}

	@Override
	public void createJob(Job job) {
		jobs.add(job);
	}

	@Override
	public Job getJobById(Long id) {
		for(Job job : jobs) {
			if(job.getId().equals(id)) {
				return job;
			}
		}
		return null;
	}

	@Override
	public boolean deleteJobById(Long id) {
		Iterator<Job> iterator = jobs.iterator();
		while (iterator.hasNext()) {
			Job job = iterator.next();
			if(job.getId().equals(id)) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean updateJob(Long id, Job updatedJob) {
		for(Job job : jobs) {
			if(job.getId().equals(id)) {
				job.setTitle(updatedJob.getTitle());
				job.setDescription(updatedJob.getDescription());
				job.setMinSalary(updatedJob.getMinSalary());
				job.setMaxSalary(updatedJob.getMaxSalary());
				job.setLocation(updatedJob.getLocation() );
				return true;
			}
		}
		return false;
	}

}
