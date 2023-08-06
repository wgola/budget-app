package com.budget.application.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.budget.application.model.Expense;
import com.budget.application.response.provider.expenses.ExpenseResponseProvider;
import com.budget.application.response.provider.expenses.ExpensesList;

@RestController
@CrossOrigin
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseResponseProvider expenseResponseProvider;

    public ExpenseController(ExpenseResponseProvider expenseResponseProvider) {
        this.expenseResponseProvider = expenseResponseProvider;
    }

    @PostMapping
    public ResponseEntity<ExpensesList> saveExpense(@RequestBody Expense expense) {
        return expenseResponseProvider.saveExpense(expense);
    }

    @DeleteMapping("/{expenseID}")
    public ResponseEntity<ExpensesList> deleteExpense(@PathVariable Long expenseID) {
        return expenseResponseProvider.deleteExpense(expenseID);
    }

    @GetMapping
    public ResponseEntity<ExpensesList> getAllExpenses() {
        return expenseResponseProvider.getAllExpenses();
    }

    @GetMapping("/criteria")
    public ResponseEntity<ExpensesList> getExpensesBySearchCriteria(
            @RequestParam List<String> tagNames,
            @RequestParam String fromDate,
            @RequestParam String toDate) {
        return expenseResponseProvider.getExpensesBySearchCriteria(tagNames, fromDate, toDate);
    }
}
