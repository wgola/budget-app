package com.budget.application.service.expenses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;
import com.budget.application.model.Tag;
import com.budget.application.repository.ExpenseRepository;
import com.budget.application.utils.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ExpensesServiceIntegrationTest {

    @Autowired
    private ExpensesService expensesService;

    @Autowired
    private ExpenseRepository expenseRepository;

    private Expense testExpense;
    private Timestamp fromDate;
    private Timestamp toDate;

    @BeforeEach
    void setUp() {
        TestUtils.generateGivenAmounOfTestExpenseObjects(
                3,
                1,
                LocalDateTime.of(2018, 11, 12, 1, 0, 0))
                .forEach(expenseRepository::save);

        testExpense = TestUtils.generateTestExpense(
                1,
                LocalDateTime.of(2018, 11, 12, 1, 0, 0));

        fromDate = Timestamp.valueOf("2018-11-09 01:02:03.123456789");
        toDate = Timestamp.valueOf("2018-11-12 01:02:03.123456789");
    }

    @AfterEach
    void tearDown() {
        expenseRepository.deleteAll();
    }

    @Test
    void testCreateExpense() {
        Expense createdExpense = expensesService.createExpense(testExpense);

        assertEquals(testExpense.getExpenseID(), createdExpense.getExpenseID());
    }

    @Test
    void testDeleteExpense() {
        Long expenseToDeleteID = expensesService.getAllExpenses()
                .get()
                .get(0)
                .getExpenseID();

        expensesService.deleteExpense(expenseToDeleteID);
        Optional<Expense> deletedExpense = expenseRepository.findById(expenseToDeleteID);

        assertTrue(deletedExpense.isEmpty());
    }

    @Test
    void testGetAllExpenses() {
        List<Expense> allExpenses = expensesService.getAllExpenses().get();

        assertEquals(3, allExpenses.size());
    }

    @Test
    void testGetExpensesByCriteriaWithTagsSettedOnly() {
        List<String> foundTags = expensesService.getAllExpenses()
                .get()
                .get(0)
                .getTags()
                .stream()
                .map(Tag::getName)
                .toList();

        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setTagNames(foundTags);

        Optional<List<Expense>> expensesFoundByCriteria = expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertTrue(expensesFoundByCriteria.isPresent());
        assertNotEquals(0, expensesFoundByCriteria.get().size());
    }

    @Test
    void testGetExpensesByCriteriaWithFromDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(fromDate);

        Optional<List<Expense>> expensesFoundByCriteria = expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertTrue(expensesFoundByCriteria.isPresent());
        assertNotEquals(0, expensesFoundByCriteria.get().size());
    }

    @Test
    void testGetExpensesByCriteriaWithToDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setToDate(toDate);

        Optional<List<Expense>> expensesFoundByCriteria = expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertTrue(expensesFoundByCriteria.isPresent());
        assertNotEquals(0, expensesFoundByCriteria.get().size());
    }

    @Test
    void testGetExpensesByCriteriaWithBothDatesSetted() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(fromDate);
        expensesSearchCriteria.setToDate(toDate);

        Optional<List<Expense>> expensesFoundByCriteria = expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertTrue(expensesFoundByCriteria.isPresent());
        assertNotEquals(0, expensesFoundByCriteria.get().size());
    }

    @Test
    void testGetExpensesByCriteriaWithAllParamsSetted() {
        List<String> foundTags = expensesService.getAllExpenses()
                .get()
                .get(0)
                .getTags()
                .stream()
                .map(Tag::getName)
                .toList();

        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setTagNames(foundTags);
        expensesSearchCriteria.setFromDate(fromDate);
        expensesSearchCriteria.setToDate(toDate);

        Optional<List<Expense>> expensesFoundByCriteria = expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertTrue(expensesFoundByCriteria.isPresent());
        assertNotEquals(0, expensesFoundByCriteria.get().size());
    }
}
