package com.budget.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
public class ExpensesServiceImplTest {

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
        this.generatedExpenses = TestUtils.generateGivenAmounOfTestExpenseObjects(10, 1,
                LocalDateTime.of(2018, 11, 12, 1, 0, 0));
        this.fromDate = Timestamp.valueOf("2018-11-09 01:02:03.123456789");
        this.toDate = Timestamp.valueOf("2018-11-12 01:02:03.123456789");
        this.testExpense = TestUtils.generateTestExpense(1, LocalDateTime.of(2018, 11, 10, 1, 0, 0));

        Mockito.when(this.expenseRepository.save(Mockito.any(Expense.class))).thenReturn(this.testExpense);
        Mockito.when(this.expenseRepository.findAll()).thenReturn(this.generatedExpenses);
    }

    @Test
    void testCreateExpense() {
        Expense createdExpense = this.expensesService.createExpense(this.testExpense);

        assertNotNull(createdExpense);
        assertEquals(createdExpense.getId(), this.testExpense.getId());
    }

    @Test
    void testDeleteExpense() {
        Expense expenseToDelete = this.expenseRepository.findAll().get(0);

        this.expensesService.deleteExpense(expenseToDelete.getId());
        Optional<Expense> deletedExpense = this.expenseRepository.findById(expenseToDelete.getId());

        assertTrue(deletedExpense.isEmpty());
    }

    @Test
    void testGetAllExpenses() {
        Optional<List<Expense>> allExpenses = this.expensesService.getAllExpenses();

        assertEquals(allExpenses.get().size(), this.generatedExpenses.size());
    }

    @Test
    void testGetExpensesByCriteriaWtihTagsSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        Expense desiredExpense = this.generatedExpenses.get(0);
        List<String> tagNames = desiredExpense.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        expensesSearchCriteria.setTagNames(tagNames);

        Expense foundExpense = this.expensesService.getExpensesBySearchCriteria(expensesSearchCriteria).get().get(0);

        assertEquals(foundExpense.getId(), desiredExpense.getId());
    }

    @Test
    void testGetExpensesByCriteriaWtihFromDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(this.fromDate);

        Optional<List<Expense>> foundExpenses = this.expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertTrue(foundExpenses.get().size() > 0);
    }

    @Test
    void testGetExpensesByCriteriaWtihToDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setToDate(this.toDate);

        Optional<List<Expense>> foundExpenses = this.expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertTrue(foundExpenses.get().size() > 0);
    }
}
