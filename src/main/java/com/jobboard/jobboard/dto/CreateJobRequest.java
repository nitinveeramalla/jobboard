package com.jobboard.jobboard.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateJobRequest {

    @NotBlank(message = "title can't be emty")
    private String title;

    @NotBlank(message = "description can't be emty")
    private String description;

    @NotBlank(message = "company can't be emty")
    private String company;

    @Min(value = 0, message = "salary must be non-negative")
    private int salary;
}
