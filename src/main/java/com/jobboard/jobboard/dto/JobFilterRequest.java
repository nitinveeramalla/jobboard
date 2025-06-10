package com.jobboard.jobboard.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
public class JobFilterRequest {

    private String  company;

    @Min(value = 0, message = "minSalary must be >= 0")
    private BigDecimal minSalary;

    @Min(value = 0, message = "maxSalary must be >= 0")
    private BigDecimal maxSalary;

    private LocalDate postedAfter;

    private LocalDate postedBefore;

    private String  location;
}
