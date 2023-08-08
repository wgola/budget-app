package com.budget.application.service.expenses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;
import com.budget.application.repository.ExpenseRepository;
import com.budget.application.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExpensesServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private ExpensesSearchCriteria expensesSearchCriteria;

    private ExpensesService expensesService;
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

        when(expenseRepository.save(any(Expense.class)))
                .thenReturn(testExpense);
        when(expenseRepository.findAll(expensesSearchCriteria.getSpecification()))
                .thenReturn(generatedExpenses);
        when(expenseRepository.findAll())
                .thenReturn(generatedExpenses);

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
    void testGetExpensesBySearchCriteria() {
        Optional<List<Expense>> foundExpenses = expensesService.getExpensesBySearchCriteria(expensesSearchCriteria);

        assertTrue(foundExpenses.isPresent());
        assertNotEquals(0, foundExpenses.get().size());
        verify(expenseRepository, times(1)).findAll(expensesSearchCriteria.getSpecification());
    }
}
