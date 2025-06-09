package com.jobboard.jobboard.controller;

import com.jobboard.jobboard.dto.CreateJobRequest;
import com.jobboard.jobboard.entity.Job;
import com.jobboard.jobboard.service.JobService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService){
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@Valid @RequestBody CreateJobRequest request) {

        Job saved =  jobService.createJob(request);
        URI location = URI.create("/api/jobs/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping
    public Page<Job> getAllJobs(
            @PageableDefault(page = 0, size = 20, sort = "postedDate", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return jobService.listAllJobs(pageable);
    }

    @GetMapping("{id}")
    public Job getJobById(@PathVariable("id") Long id){
        return jobService.getJobById(id);
    }

    @PutMapping("/{id}")
    public Job updateJob(@PathVariable("id") Long id,
                         @Valid @RequestBody CreateJobRequest request) {
        return jobService.updateJob(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJob(@PathVariable("id") Long id) {
        jobService.deleteJobById(id);
    }

    @GetMapping("/search")
    public List<Job> searchJobs(@RequestParam String title) {
        return jobService.searchByTitle(title);
    }

}
