package com.budget.application.response.provider.expenses;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;
import com.budget.application.model.Tag;
import com.budget.application.service.expenses.ExpensesService;
import com.budget.application.service.tags.TagService;
import com.budget.application.utils.CommonTools;

@Service
public class ExpenseResponseProvider {

    private final ExpensesService expensesService;

    private final TagService tagService;

    public ExpenseResponseProvider(ExpensesService expensesService, TagService tagService) {
        this.expensesService = expensesService;
        this.tagService = tagService;
    }

    public ResponseEntity<ExpensesList> getAllExpenses() {
        try {
            List<Expense> retrievedExpenses = expensesService.getAllExpenses().get();
            ExpensesList body = new ExpensesList(retrievedExpenses);

            return new ResponseEntity<ExpensesList>(body, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ExpensesList>(new ExpensesList(), HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<ExpensesList> saveExpense(Expense expense) {
        try {
            if (expense.getCreationDate() == null)
                expense.setCreationDate(LocalDateTime.now());

            List<Tag> newTags = expense.getTags()
                    .stream()
                    .map(Tag::getName)
                    .map(tagService::createTag)
                    .toList();

            expense.setTags(newTags);

            Expense createdExpense = expensesService.createExpense(expense);
            ExpensesList body = new ExpensesList(Arrays.asList(createdExpense));

            return new ResponseEntity<ExpensesList>(body, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ExpensesList>(new ExpensesList(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public ResponseEntity<ExpensesList> deleteExpense(Long expenseID) {
        try {
            expensesService.deleteExpense(expenseID);

            return new ResponseEntity<ExpensesList>(new ExpensesList(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ExpensesList>(new ExpensesList(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ExpensesList> getExpensesBySearchCriteria(List<String> tagNames, String fromDate,
            String toDate) {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();

        if (!tagNames.isEmpty())
            expensesSearchCriteria.setTagNames(tagNames);

        if (!StringUtils.hasLength(fromDate)) {
            try {
                Timestamp fDate = CommonTools.getTimestampFromISODate(fromDate);
                expensesSearchCriteria.setFromDate(fDate);
            } catch (Exception e) {
                expensesSearchCriteria.setFromDate(null);
            }
        }

        if (!StringUtils.hasLength(fromDate)) {
            try {
                Timestamp tDate = CommonTools.getTimestampFromISODate(toDate);
                expensesSearchCriteria.setFromDate(tDate);
            } catch (Exception e) {
                expensesSearchCriteria.setFromDate(null);
            }
        }

        try {
            List<Expense> retrievedExpenses = expensesService.getExpensesBySearchCriteria(expensesSearchCriteria)
                    .get();
            ExpensesList body = new ExpensesList(retrievedExpenses);

            return new ResponseEntity<ExpensesList>(body, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ExpensesList>(new ExpensesList(), HttpStatus.NO_CONTENT);
        }
    }
}
