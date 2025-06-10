package com.jobboard.jobboard.mapper;

import com.jobboard.jobboard.dto.CreateJobRequest;
import com.jobboard.jobboard.dto.JobDto;
import com.jobboard.jobboard.entity.Job;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper {
    JobDto toDto(Job job);
    Job mapToEntity(CreateJobRequest request);
}
