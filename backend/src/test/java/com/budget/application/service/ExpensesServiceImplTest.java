package com.budget.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.budget.application.model.Expense;
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
        this.generatedExpenses = TestUtils.generateGivenAmounOfTestExpenseObjects(1, 1,
                LocalDateTime.of(2018, 11, 12, 1, 0, 0));
        this.fromDate = Timestamp.valueOf("2018-11-09 01:02:03.123456789");
        this.toDate = Timestamp.valueOf("2018-11-12 01:02:03.123456789");
        this.testExpense = TestUtils.generateTestExpense(1, LocalDateTime.of(2018, 11, 10, 1, 0, 0));

        Mockito.when(this.expenseRepository.save(Mockito.any(Expense.class))).thenReturn(this.testExpense);
    }

    @Test
    void testCreateExpense() {
        Expense createdExpense = this.expensesService.createExpense(this.testExpense);

        assertNotNull(createdExpense);
        assertEquals(createdExpense.getId(), this.testExpense.getId());
    }

    @Test
    void testDeleteExpense() {

    }

    @Test
    void testGetAllExpenses() {

    }

    @Test
    void testGetExpensesBySearchCriteria() {

    }
}
