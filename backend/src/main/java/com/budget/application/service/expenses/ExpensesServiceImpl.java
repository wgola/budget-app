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
        List<Expense> filteredExpenses = expenseRepository.findAll();

        if (criteria.getTagNames() != null)
            filteredExpenses = filteredExpenses.stream()
                    .filter(expense -> expense
                            .getTags()
                            .stream()
                            .anyMatch(tag -> criteria
                                    .getTagNames()
                                    .contains(tag.getName())))
                    .toList();

        if (criteria.getFromDate() != null)
            filteredExpenses = filteredExpenses.stream()
                    .filter(expense -> expense
                            .getCreationDate()
                            .isAfter(criteria
                                    .getFromDate()
                                    .toLocalDateTime()))
                    .toList();

        if (criteria.getToDate() != null)
            filteredExpenses = filteredExpenses.stream()
                    .filter(expense -> expense
                            .getCreationDate()
                            .isBefore(criteria
                                    .getToDate()
                                    .toLocalDateTime()))
                    .toList();

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
