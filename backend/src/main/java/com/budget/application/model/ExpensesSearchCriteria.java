package com.budget.application.model;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpensesSearchCriteria {

    private Timestamp fromDate;
    private Timestamp toDate;
    private List<String> tagNames;
}
