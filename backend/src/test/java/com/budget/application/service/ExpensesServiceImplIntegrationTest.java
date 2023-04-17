package com.budget.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;
import com.budget.application.model.Tag;
import com.budget.application.repository.ExpenseRepository;
import com.budget.application.utils.TestUtils;

@SpringBootTest
public class ExpensesServiceImplIntegrationTest {

    @Autowired
    private ExpensesService expensesService;

    @Autowired
    private ExpenseRepository expenseRepository;

    private Expense testExpense;
    private Timestamp fromDate;
    private Timestamp toDate;

    @BeforeEach
    void setUp() {
        this.testExpense = TestUtils.generateTestExpense(1, LocalDateTime.of(2018, 11, 12, 1, 0, 0));
        this.fromDate = Timestamp.valueOf("2018-11-09 01:02:03.123456789");
        this.toDate = Timestamp.valueOf("2018-11-12 01:02:03.123456789");
        for (int i = 0; i < 10; i++) {
            this.expensesService
                    .createExpense(TestUtils.generateTestExpense(1, LocalDateTime.of(2018, 11, 12, 1, 0, 0)));
        }
    }

    @Test
    void testCreateExpense() {
        Expense createdExpense = this.expensesService.createExpense(this.testExpense);

        assertEquals(createdExpense.getExpenseID(), this.testExpense.getExpenseID());
    }

    @Test
    void testDeleteExpense() {
        Long expenseToDeleteID = this.expensesService.getAllExpenses().get().get(0).getExpenseID();
        this.expensesService.deleteExpense(expenseToDeleteID);
        Optional<Expense> deletedExpense = this.expenseRepository.findById(expenseToDeleteID);

        assertTrue(deletedExpense.isEmpty());
    }

    @Test
    void testGetAllExpenses() {
        List<Expense> allExpenses = this.expensesService.getAllExpenses().get();

        assertTrue(allExpenses.size() > 0);
    }

    @Test
    void testGetExpensesByCriteriaWithTagsSettedOnly() {
        List<String> foundTags = this.expensesService.getAllExpenses().get().get(0).getTags().stream().map(Tag::getName)
                .toList();
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setTagNames(foundTags);

        Optional<List<Expense>> expensesFoundByCriteria = this.expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertTrue(expensesFoundByCriteria.isPresent());
        assertTrue(expensesFoundByCriteria.get().size() > 0);
    }

    @Test
    void testGetExpensesByCriteriaWithFromDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(this.fromDate);

        Optional<List<Expense>> expensesFoundByCriteria = this.expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertTrue(expensesFoundByCriteria.isPresent());
        assertTrue(expensesFoundByCriteria.get().size() > 0);
    }

    @Test
    void testGetExpensesByCriteriaWithToDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setToDate(this.toDate);

        Optional<List<Expense>> expensesFoundByCriteria = this.expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertTrue(expensesFoundByCriteria.isPresent());
        assertTrue(expensesFoundByCriteria.get().size() > 0);
    }

    @Test
    void testGetExpensesByCriteriaWithBothDatesSetted() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(this.fromDate);
        expensesSearchCriteria.setToDate(this.toDate);

        Optional<List<Expense>> expensesFoundByCriteria = this.expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertTrue(expensesFoundByCriteria.isPresent());
        assertTrue(expensesFoundByCriteria.get().size() > 0);
    }

    @Test
    void testGetExpensesByCriteriaWithAllParamsSetted() {
        List<String> foundTags = this.expensesService.getAllExpenses().get().get(0).getTags().stream().map(Tag::getName)
                .toList();
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setTagNames(foundTags);
        expensesSearchCriteria.setFromDate(this.fromDate);
        expensesSearchCriteria.setToDate(this.toDate);

        Optional<List<Expense>> expensesFoundByCriteria = this.expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertTrue(expensesFoundByCriteria.isPresent());
        assertTrue(expensesFoundByCriteria.get().size() > 0);
    }
}
