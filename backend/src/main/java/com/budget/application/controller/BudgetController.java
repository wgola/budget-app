package com.budget.application.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.budget.application.model.Expense;

@RestController
public class BudgetController {

    @PostMapping(value = "/expense")
    public Object addNewExpense(@RequestBody Expense expense) {

        return null;
    }

    @DeleteMapping(value = "/expense/{expenseID}")
    public Object deleteExpense(@PathVariable("expenseID") Long expenseID) {

        return null;
    }

    @GetMapping(value = "/expenses")
    public Object getAllExpenses() {

        return null;
    }

    @GetMapping(value = "/expense/criteria")
    public Object getExpensesBySearchCriteria(@RequestParam(value = "tagNames") List<String> tagNames,
            @RequestParam(value = "fromDate") String fromDate, @RequestParam(value = "toDate") String toDate) {

        return null;
    }

    @GetMapping(value = "tags")
    public Object getAllTags() {

        return null;
    }

    @PostMapping(value = "tag")
    public Object addNewTag(@RequestBody String name) {

        return null;
    }

    @DeleteMapping(value = "tag/{tagID}")
    public Object deleteTag(@PathVariable("tagID") Long tagID) {

        return null;
    }
}
