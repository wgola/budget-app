package com.budget.application.service.expenses;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;
import com.budget.application.model.Tag;
import com.budget.application.repository.ExpenseRepository;
import com.budget.application.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExpensesServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    private ExpensesService expensesService;
    private Timestamp fromDate;
    private Timestamp toDate;
    private List<Expense> generatedExpenses;
    private Expense testExpense;

    @BeforeEach
    void setUp() {
        generatedExpenses = TestUtils
                .generateGivenAmounOfTestExpenseObjects(
                        3,
                        1,
                        LocalDateTime.of(2018, 11, 12, 1, 0, 0));

        testExpense = TestUtils
                .generateTestExpense(
                        1,
                        LocalDateTime.of(2018, 11, 12, 1, 0, 0));

        fromDate = Timestamp.valueOf("2018-11-09 01:02:03.123456789");
        toDate = Timestamp.valueOf("2018-11-12 01:02:03.123456789");

        Mockito.when(expenseRepository.save(Mockito.any(Expense.class))).thenReturn(testExpense);
        Mockito.when(expenseRepository.findAll()).thenReturn(generatedExpenses);

        expensesService = new ExpensesServiceImpl(expenseRepository);
    }

    @Test
    void testCreateExpense() {
        Expense createdExpense = expensesService.createExpense(testExpense);

        assertNotNull(createdExpense);
        assertEquals(testExpense.getExpenseID(), createdExpense.getExpenseID());
    }

    @Test
    void testDeleteExpense() {
        Expense expenseToDelete = expenseRepository.findAll().get(0);

        expensesService.deleteExpense(expenseToDelete.getExpenseID());
        Optional<Expense> deletedExpense = expenseRepository.findById(expenseToDelete.getExpenseID());

        assertTrue(deletedExpense.isEmpty());
    }

    @Test
    void testGetAllExpenses() {
        Optional<List<Expense>> allExpenses = expensesService.getAllExpenses();

        assertEquals(generatedExpenses.size(), allExpenses.get().size());
    }

    @Test
    void testGetExpensesByCriteriaWtihTagsSettedOnly() {
        List<String> tagNames = generatedExpenses.get(0)
                .getTags()
                .stream()
                .map(Tag::getName)
                .toList();

        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setTagNames(tagNames);

        Optional<List<Expense>> foundExpenses = expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertTrue(foundExpenses.isPresent());
        assertNotEquals(0, foundExpenses.get().size());
    }

    @Test
    void testGetExpensesByCriteriaWtihFromDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(fromDate);

        Optional<List<Expense>> foundExpenses = expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertNotEquals(0, foundExpenses.get().size());
    }

    @Test
    void testGetExpensesByCriteriaWtihToDateSettedOnly() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setToDate(toDate);

        Optional<List<Expense>> foundExpenses = expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertNotEquals(0, foundExpenses.get().size());
    }

    @Test
    void testGetExpensesByCriteriaWtihBothDatesSetted() {
        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setFromDate(fromDate);
        expensesSearchCriteria.setToDate(toDate);

        Optional<List<Expense>> foundExpenses = expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertNotEquals(0, foundExpenses.get().size());
    }

    @Test
    void testGetExpensesByCriteriaWtihAllParamsSetted() {
        List<String> tagNames = generatedExpenses.get(0)
                .getTags()
                .stream()
                .map(Tag::getName)
                .toList();

        ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
        expensesSearchCriteria.setTagNames(tagNames);
        expensesSearchCriteria.setFromDate(fromDate);
        expensesSearchCriteria.setToDate(toDate);

        Optional<List<Expense>> foundExpenses = expensesService
                .getExpensesBySearchCriteria(expensesSearchCriteria);

        assertNotEquals(0, foundExpenses.get().size());
    }
}
