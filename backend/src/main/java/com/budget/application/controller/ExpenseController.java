package com.budget.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.budget.application.model.Expense;
import com.budget.application.response.provider.ExpenseResponseProvider;
import com.budget.application.response.provider.ExpensesList;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseResponseProvider expenseResponseProvider;

    @CrossOrigin
    @PostMapping(value = "/expense")
    public ResponseEntity<ExpensesList> addNewExpense(@RequestBody Expense expense) {
        return this.expenseResponseProvider.createExpense(expense);
    }

    @CrossOrigin
    @DeleteMapping(value = "/expense/{expenseID}")
    public ResponseEntity<ExpensesList> deleteExpense(@PathVariable Long expenseID) {
        return this.expenseResponseProvider.deleteExpense(expenseID);
    }

    @CrossOrigin
    @GetMapping(value = "/expense")
    public ResponseEntity<ExpensesList> getAllExpenses() {
        return this.expenseResponseProvider.getAllExpenses();
    }

    @CrossOrigin
    @GetMapping(value = "/expense/criteria")
    public ResponseEntity<ExpensesList> getExpensesBySearchCriteria(
            @RequestParam List<String> tagNames,
            @RequestParam String fromDate, @RequestParam String toDate) {
        return this.expenseResponseProvider.getExpensesBySearchCriteria(tagNames, fromDate, toDate);
    }
}