package com.jobboard.jobboard.controller;

import com.jobboard.jobboard.dto.CreateJobRequest;
import com.jobboard.jobboard.dto.JobDto;
import com.jobboard.jobboard.dto.JobFilterRequest;
import com.jobboard.jobboard.entity.Job;
import com.jobboard.jobboard.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Jobs", description = "Create, read, update, delete job postings")
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService){
        this.jobService = jobService;
    }

    @Operation(summary = "Create a new job posting")
    @PostMapping
    public ResponseEntity<JobDto> createJob(@Valid @RequestBody CreateJobRequest request) {

        JobDto saved =  jobService.createJob(request);
        URI location = URI.create("/api/jobs/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @Operation(summary = "List all jobs",
            parameters = {
                    @Parameter(name="page", description="zero-based page index"),
                    @Parameter(name="size", description="items per page")
            })
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Job updateJob(@PathVariable("id") Long id,
                         @Valid @RequestBody CreateJobRequest request) {
        return jobService.updateJob(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJob(@PathVariable("id") Long id) {
        jobService.deleteJobById(id);
    }

    @GetMapping("/search")
    public List<Job> searchJobs(@RequestParam String title) {
        return jobService.searchByTitle(title);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<JobDto>> filterJobs(
            JobFilterRequest filter,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<JobDto> page = jobService.filterJobs(filter, pageable);
        return ResponseEntity.ok(page);
    }
}
