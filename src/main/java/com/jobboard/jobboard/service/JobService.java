package com.jobboard.jobboard.service;

import com.jobboard.jobboard.dto.CreateJobRequest;
import com.jobboard.jobboard.entity.Job;
import com.jobboard.jobboard.repository.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository){
        this.jobRepository = jobRepository;
    }

    public Job createJob(CreateJobRequest request){

        return jobRepository.save(mapToEntity(request));
    }

    public Page<Job> listAllJobs(Pageable pageable){
        return jobRepository.findAll(pageable);
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Job not found " + id));
    }

    public Job updateJob(Long id, CreateJobRequest request){
        Job existingJob = getJobById(id);
        existingJob.setTitle(request.getTitle());
        existingJob.setDescription(request.getDescription());
        existingJob.setCompany(request.getCompany());
        existingJob.setSalary(request.getSalary());
        return jobRepository.save(existingJob);
    }

    public void deleteJobById(Long id){
        if (!jobRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Job not found: " + id);
        }
        jobRepository.deleteById(id);
    }

    public List<Job> searchByTitle(String keyword) {
        return jobRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public Job mapToEntity(CreateJobRequest request){
        LocalDateTime postedDate = LocalDateTime.now();
        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompany(request.getCompany());
        job.setSalary(request.getSalary());
        job.setPostedDate(postedDate);
        return job;
    }
}
