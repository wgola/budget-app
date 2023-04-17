package com.budget.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;
import com.budget.application.model.Tag;
import com.budget.application.repository.ExpenseRepository;
import com.budget.application.utils.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ExpensesServiceImplTest {

    @InjectMocks
    private ExpensesServiceImpl expensesService;

    @Mock
    private ExpenseRepository expenseRepository;

    private Timestamp fromDate;
    private Timestamp toDate;
    private List<Expense> generatedExpenses;
    private Expense testExpense;

    @BeforeEach
    void setUp() {
        this.generatedExpenses = TestUtils.generateGivenAmounOfTestExpenseObjects(4, 1,
                LocalDateTime.of(2018, 11, 12, 1, 0, 0));
        this.testExpense = this.generatedExpenses.get(0);
        this.fromDate = Timestamp.valueOf("2018-11-09 01:02:03.123456789");
        this.toDate = Timestamp.valueOf("2018-11-12 01:02:03.123456789");

        Mockito.when(this.expenseRepository.save(Mockito.any(Expense.class))).thenReturn(this.testExpense);
        Mockito.when(this.expenseRepository.findAll()).thenReturn(this.generatedExpenses);
    }

    @Test
    void testCreateExpense() {
        Expense createdExpense = this.expensesService.createExpense(this.testExpense);

        assertNotNull(createdExpense);
        assertEquals(this.testExpense.getExpenseID(), createdExpense.getExpenseID());
    }

    @Test
    void testDeleteExpense() {
        Expense expenseToDelete = this.expenseRepository.findAll().get(0);

        this.expensesService.deleteExpense(expenseToDelete.getExpenseID());
        Optional<Expense> deletedExpense = this.expenseRepository.findById(expenseToDelete.getExpenseID());

        assertTrue(deletedExpense.isEmpty());
    }

    @Test
    void testGetAllExpenses() {
        Optional<List<Expense>> allExpenses = this.expensesService.getAllExpenses();

        assertEquals(this.generatedExpenses.size(), allExpenses.get().size());
    }

    @Test
    void testGetExpensesByCriteriaWtihTagsSettedOnly() {
        List<String> tagNames = this.generatedExpenses.get(0).getTags().stream().map(Tag::getName).toList();
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setTagNames(tagNames);

        Optional<List<Expense>> foundExpenses = this.expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertTrue(foundExpenses.isPresent());
        assertNotEquals(0, foundExpenses.get().size());
    }

    @Test
    void testGetExpensesByCriteriaWtihFromDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(this.fromDate);

        Optional<List<Expense>> foundExpenses = this.expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertNotEquals(0, foundExpenses.get().size());
    }

    @Test
    void testGetExpensesByCriteriaWtihToDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setToDate(this.toDate);

        Optional<List<Expense>> foundExpenses = this.expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertNotEquals(0, foundExpenses.get().size());
    }

    @Test
    void testGetExpensesByCriteriaWtihBothDatesSetted() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(this.fromDate);
        expensesSearchCriteria.setToDate(this.toDate);

        Optional<List<Expense>> foundExpenses = this.expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertNotEquals(0, foundExpenses.get().size());
    }

    @Test
    void testGetExpensesByCriteriaWtihAllParamsSetted() {
        List<String> tagNames = this.generatedExpenses.get(0).getTags().stream().map(Tag::getName).toList();
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setTagNames(tagNames);
        expensesSearchCriteria.setFromDate(this.fromDate);
        expensesSearchCriteria.setToDate(this.toDate);

        Optional<List<Expense>> foundExpenses = this.expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertNotEquals(0, foundExpenses.get().size());
    }
}
