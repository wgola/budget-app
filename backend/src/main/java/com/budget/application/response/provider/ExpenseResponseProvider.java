package com.budget.application.response.provider;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;
import com.budget.application.model.Tag;
import com.budget.application.service.ExpensesService;
import com.budget.application.service.TagService;
import com.budget.application.utils.CommonTools;

@Service
public class ExpenseResponseProvider {

    @Autowired
    private ExpensesService expensesService;

    @Autowired
    private TagService tagService;

    public ResponseEntity<ExpensesList> getAllExpenses() {
        try {
            List<Expense> retrievedExpenses = this.expensesService.getAllExpenses().get();
            ExpensesList body = new ExpensesList(retrievedExpenses);

            return new ResponseEntity<ExpensesList>(body, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ExpensesList>(new ExpensesList(), HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<ExpensesList> saveExpense(Expense expense) {
        try {
            if (!StringUtils.hasLength(expense.getFormattedDate())) {
                expense.setCreationDate(LocalDateTime.now());
            } else {
                expense.setCreationDate(CommonTools.getLocalDateTimeFromISODate(expense.getFormattedDate()));
            }
            List<Tag> tagsFromExpense = expense.getTags();
            List<Tag> newTags = new ArrayList<Tag>();
            for (Tag currenTag : tagsFromExpense) {
                newTags.add(tagService.createTag(currenTag.getName()));
            }
            expense.setTags(newTags);
            Expense createdExpense = this.expensesService.createExpense(expense);
            ExpensesList body = new ExpensesList(Arrays.asList(createdExpense));

            return new ResponseEntity<ExpensesList>(body, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ExpensesList>(new ExpensesList(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public ResponseEntity<ExpensesList> deleteExpense(Long expenseID) {
        try {
            this.expensesService.deleteExpense(expenseID);

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
            List<Expense> retrievedExpenses = this.expensesService.getExpensesBySearchCriteria(expensesSearchCriteria)
                    .get();
            ExpensesList body = new ExpensesList(retrievedExpenses);

            return new ResponseEntity<ExpensesList>(body, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ExpensesList>(new ExpensesList(), HttpStatus.NO_CONTENT);
        }
    }
}
