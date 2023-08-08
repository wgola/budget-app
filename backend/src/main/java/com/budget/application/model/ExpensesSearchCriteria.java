package com.budget.application.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpensesSearchCriteria {

    private Timestamp fromDate;
    private Timestamp toDate;
    private List<String> tagNames;

    public Specification<Expense> getSpecification() {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (fromDate != null)
                predicates.add(criteriaBuilder.greaterThan(
                        root.get("creationDate"),
                        fromDate));

            if (toDate != null)
                predicates.add(criteriaBuilder.lessThan(
                        root.get("creationDate"),
                        toDate));

            if (tagNames != null) {
                Join<Expense, Tag> expenseTags = root.join("tags");
                predicates.add(expenseTags.get("name").in(tagNames));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
