package com.budget.application.response.provider;

import java.util.List;

import com.budget.application.model.Expense;

public class ExpensesList {

    private List<Expense> expenses;

    public ExpensesList() {
    }

    public ExpensesList(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Expense> getExpenses() {
        return this.expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
