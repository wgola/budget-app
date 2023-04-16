package com.budget.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.budget.application.model.Expense;
import com.budget.application.repository.ExpenseRepository;
import com.budget.application.utils.TestUtils;

@SpringBootTest
public class ExpensesServiceImplIntegrationTest {

    @Autowired
    private ExpensesService expensesService;

    @Autowired
    private ExpenseRepository expenseRepository;

    private Expense testExpense;

    @BeforeEach
    void setUp() {
        this.testExpense = TestUtils.generateTestExpense(1, LocalDateTime.now());
        for (int i = 0; i < 10; i++) {
            this.expensesService.createExpense(TestUtils.generateTestExpense(1, LocalDateTime.now()));
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
}
