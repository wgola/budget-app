package com.budget.application.model;

import java.security.Timestamp;
import java.util.List;

public class ExpensesSearchCriteria {

    private Timestamp fromDate;
    private Timestamp toDate;
    private List<String> tagNames;

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }
}
