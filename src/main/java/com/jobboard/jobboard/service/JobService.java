package com.jobboard.jobboard.service;

import com.jobboard.jobboard.dto.CreateJobRequest;
import com.jobboard.jobboard.dto.JobDto;
import com.jobboard.jobboard.dto.JobFilterRequest;
import com.jobboard.jobboard.entity.Job;
import com.jobboard.jobboard.mapper.JobMapper;
import com.jobboard.jobboard.repository.JobRepository;
import com.jobboard.jobboard.specification.JobSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class JobService {

    private final JobRepository jobRepository;

    private static final Logger log = LoggerFactory.getLogger(JobService.class);

    private final JobMapper jobMapper;

    public JobService(JobRepository jobRepository, JobMapper jobMapper){
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
    }

    public JobDto createJob(CreateJobRequest request){
        log.info("Attempting to create job");
        Job job = jobMapper.mapToEntity(request);
        Job saved = jobRepository.save(job);
        return jobMapper.toDto(saved);
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
        log.info("updating Job");
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
        log.info("Attempting to delete Job");
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
        log.info("Job created"+ job);
        return job;
    }

    public Page<JobDto> filterJobs(JobFilterRequest filter, Pageable pg) {
        // start with a no-op spec
        Specification<Job> spec = Specification.where((root, query, cb) -> cb.conjunction());

        if (filter.getCompany() != null && !filter.getCompany().isBlank()) {
            spec = spec.and(JobSpecification.companyContains(filter.getCompany()));
        }
        if (filter.getMinSalary() != null || filter.getMaxSalary() != null) {
            spec = spec.and(JobSpecification.salaryBetween(filter.getMinSalary(), filter.getMaxSalary()));
        }
        if (filter.getPostedAfter() != null || filter.getPostedBefore() != null) {
            spec = spec.and(JobSpecification.postedBetween(filter.getPostedAfter(), filter.getPostedBefore()));
        }
        if (filter.getLocation() != null && !filter.getLocation().isBlank()) {
            spec = spec.and(JobSpecification.locationContains(filter.getLocation()));
        }

        return jobRepository.findAll(spec, pg)
                .map(jobMapper::toDto);
    }
}
