package com.budget.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.budget.application.model.Expense;
import com.budget.application.response.provider.ExpensesList;
import com.budget.application.utils.TestUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class ExpenseControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    private String createURLWithPort(String uri) {
        return "http://localhost:" + this.port + uri;
    }

    @Test
    void testAddExpense() {
        Expense expenseToAdd = TestUtils.generateTestExpense(1, LocalDateTime.now());
        HttpEntity<Expense> addExpensEntity = new HttpEntity<>(expenseToAdd, this.headers);
        ResponseEntity<ExpensesList> addExpenseResponse = this.restTemplate
                .postForEntity(this.createURLWithPort("/expense"), addExpensEntity, ExpensesList.class);

        assertEquals(HttpStatus.CREATED, addExpenseResponse.getStatusCode());
        assertNotEquals(0, addExpenseResponse.getBody().getExpenses().size());
    }

    @Test
    void testGetExpenses() {
        Expense expenseToAdd = TestUtils.generateTestExpense(1, LocalDateTime.now());
        HttpEntity<Expense> addExpensEntity = new HttpEntity<>(expenseToAdd, this.headers);
        this.restTemplate.postForEntity(this.createURLWithPort("/expense"), addExpensEntity, ExpensesList.class);

        ResponseEntity<ExpensesList> getExpenseResponse = this.restTemplate
                .getForEntity(this.createURLWithPort("/expense"), ExpensesList.class);

        assertEquals(HttpStatus.OK, getExpenseResponse.getStatusCode());
        assertNotEquals(0, getExpenseResponse.getBody().getExpenses().size());
    }

    @Test
    void testDeleteExpense() {
        Expense expenseToAdd = TestUtils.generateTestExpense(1, LocalDateTime.now());
        HttpEntity<Expense> addExpensEntity = new HttpEntity<>(expenseToAdd, this.headers);
        ResponseEntity<ExpensesList> addExpenseResponse = this.restTemplate
                .postForEntity(this.createURLWithPort("/expense"), addExpensEntity, ExpensesList.class);

        Long addedExpenseID = addExpenseResponse.getBody().getExpenses().get(0).getExpenseID();

        ResponseEntity<ExpensesList> deleteExpenseResponse = this.restTemplate.exchange(
                this.createURLWithPort("/expense/" + addedExpenseID), HttpMethod.DELETE, addExpenseResponse,
                ExpensesList.class);

        assertEquals(HttpStatus.OK, deleteExpenseResponse.getStatusCode());
        assertNull(deleteExpenseResponse.getBody().getExpenses());
    }

    @Test
    void testGetExpensesBySearchCriteriaWithTagNamesSettedOnly() {
        Expense expenseToAdd = TestUtils.generateTestExpense(1, LocalDateTime.now());
        HttpEntity<Expense> addExpenseEntity = new HttpEntity<Expense>(expenseToAdd, this.headers);
        ResponseEntity<ExpensesList> addExpenseResponse = this.restTemplate
                .postForEntity(this.createURLWithPort("/expense"), addExpenseEntity, ExpensesList.class);

        String tagName = addExpenseResponse.getBody().getExpenses().get(0).getTags().get(0).getName();
        String searchCriteriaURL = this
                .createURLWithPort("/expense/criteria?tagNames=" + tagName + "&fromDate=&toDate=");

        ResponseEntity<ExpensesList> getExpensesByCriteriaResponse = this.restTemplate.getForEntity(searchCriteriaURL,
                ExpensesList.class);

        assertEquals(HttpStatus.OK, getExpensesByCriteriaResponse.getStatusCode());
        assertEquals(1, getExpensesByCriteriaResponse.getBody().getExpenses().size());
    }
}
