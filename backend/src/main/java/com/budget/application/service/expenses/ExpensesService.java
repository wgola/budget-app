package com.budget.application.service.expenses;

import java.util.Optional;
import java.util.List;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;

public interface ExpensesService {

    Optional<List<Expense>> getExpensesBySearchCriteria(ExpensesSearchCriteria criteria);

    Expense createExpense(Expense expense);

    Optional<List<Expense>> getAllExpenses();

    void deleteExpense(Long expenseId);
}
