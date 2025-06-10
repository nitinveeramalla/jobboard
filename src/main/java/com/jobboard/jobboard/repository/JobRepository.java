package com.jobboard.jobboard.repository;

import com.jobboard.jobboard.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    List<Job> findByCompany(String company);

     List<Job> findByTitleContainingIgnoreCase(String keyword);
}
