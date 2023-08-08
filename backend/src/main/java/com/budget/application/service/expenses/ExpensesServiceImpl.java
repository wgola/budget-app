package com.budget.application.service.expenses;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;
import com.budget.application.repository.ExpenseRepository;

@Service
public class ExpensesServiceImpl implements ExpensesService {

    private final ExpenseRepository expenseRepository;

    public ExpensesServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Optional<List<Expense>> getExpensesBySearchCriteria(ExpensesSearchCriteria criteria) {
        List<Expense> filteredExpenses = expenseRepository.findAll(criteria.getSpecification());

        return Optional.of(filteredExpenses);
    }

    @Override
    public void deleteExpense(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }

    @Override
    public Optional<List<Expense>> getAllExpenses() {
        List<Expense> allExpenses = expenseRepository.findAll();

        return Optional.of(allExpenses);
    }
}
