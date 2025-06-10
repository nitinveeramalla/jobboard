package com.jobboard.jobboard.specification;

import com.jobboard.jobboard.entity.Job;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class JobSpecification {

    public static Specification<Job> companyContains(String company) {
        return (root, query, cb) -> cb.like(
                cb.lower(root.get("company")),
                "%" + company.toLowerCase() + "%"
        );
    }

    public static Specification<Job> salaryBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min != null && max != null) {
                return cb.between(root.get("salary"), min, max);
            } else if (min != null) {
                return cb.greaterThanOrEqualTo(root.get("salary"), min);
            } else if (max != null) {
                return cb.lessThanOrEqualTo(root.get("salary"), max);
            } else {
                return cb.conjunction();
            }
        };
    }

    public static Specification<Job> postedBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            if (start != null && end != null) {
                return cb.between(root.get("postedDate"), start, end);
            } else if (start != null) {
                return cb.greaterThanOrEqualTo(root.get("postedDate"), start);
            } else if (end != null) {
                return cb.lessThanOrEqualTo(root.get("postedDate"), end);
            } else {
                return cb.conjunction();
            }
        };
    }

    public static Specification<Job> locationContains(String location) {
        return (root, query, cb) -> cb.like(
                cb.lower(root.get("location")),
                "%" + location.toLowerCase() + "%"
        );
    }
}
