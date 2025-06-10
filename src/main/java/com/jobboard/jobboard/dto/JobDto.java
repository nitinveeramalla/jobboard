package com.jobboard.jobboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class JobDto {

        private Long       id;
        private String     title;
        private String     description;
        private String     company;
        private LocalDate postedDate;
        private BigDecimal salary;
        private String     location;
}
