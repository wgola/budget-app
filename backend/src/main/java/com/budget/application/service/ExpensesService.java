package com.budget.application.service;

import java.util.Optional;
import java.util.List;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;

public interface ExpensesService {

    public Optional<List<Expense>> getExpensesBySearchCriteria(ExpensesSearchCriteria criteria);

    public Expense createExpense(Expense expense);

    public Optional<List<Expense>> getAllExpenses();

    public void deleteExpense(Long expenseId);
}
