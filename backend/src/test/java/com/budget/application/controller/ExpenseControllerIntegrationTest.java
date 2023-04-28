package com.budget.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
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
    private Timestamp fromDate = Timestamp.valueOf("2018-11-09 01:02:03.123456789");
    private Timestamp toDate = Timestamp.valueOf("2018-11-12 01:02:03.123456789");
    private HttpEntity<Expense> addExpenseEntity;

    private String createURLWithPort(String uri) {
        StringBuilder result = new StringBuilder("http://localhost:")
                .append(this.port)
                .append(uri);

        return result.toString();
    }

    private String createURLWithPort(String uri, String tagName, String fromDate, String toDate) {
        StringBuilder result = new StringBuilder("http://localhost:")
                .append(this.port)
                .append(uri)
                .append("?tagNames=")
                .append(tagName)
                .append("&fromDate=")
                .append(fromDate)
                .append("&toDate=")
                .append(toDate);

        return result.toString();
    }

    @BeforeEach
    void setUp() {
        Expense expenseToAdd = TestUtils.generateTestExpense(1, LocalDateTime.of(2018, 11, 12, 1, 0, 0));
        this.addExpenseEntity = new HttpEntity<>(expenseToAdd, this.headers);
    }

    @Test
    void testAddExpense() {
        ResponseEntity<ExpensesList> addExpenseResponse = this.restTemplate
                .postForEntity(this.createURLWithPort("/expense"), this.addExpenseEntity, ExpensesList.class);

        assertEquals(HttpStatus.CREATED, addExpenseResponse.getStatusCode());
        assertNotEquals(0, addExpenseResponse.getBody().getExpenses().size());
    }

    @Test
    void testGetExpenses() {
        this.restTemplate.postForEntity(this.createURLWithPort("/expense"), this.addExpenseEntity, ExpensesList.class);

        ResponseEntity<ExpensesList> getExpenseResponse = this.restTemplate
                .getForEntity(this.createURLWithPort("/expense"), ExpensesList.class);

        assertEquals(HttpStatus.OK, getExpenseResponse.getStatusCode());
        assertNotEquals(0, getExpenseResponse.getBody().getExpenses().size());
    }

    @Test
    void testDeleteExpense() {
        ResponseEntity<ExpensesList> addExpenseResponse = this.restTemplate
                .postForEntity(this.createURLWithPort("/expense"), this.addExpenseEntity, ExpensesList.class);

        Long addedExpenseID = addExpenseResponse.getBody().getExpenses().get(0).getExpenseID();

        ResponseEntity<ExpensesList> deleteExpenseResponse = this.restTemplate.exchange(
                this.createURLWithPort("/expense/" + addedExpenseID), HttpMethod.DELETE, addExpenseResponse,
                ExpensesList.class);

        assertEquals(HttpStatus.OK, deleteExpenseResponse.getStatusCode());
        assertNull(deleteExpenseResponse.getBody().getExpenses());
    }

    @Test
    void testGetExpensesBySearchCriteriaWithTagNamesSettedOnly() {
        ResponseEntity<ExpensesList> addExpenseResponse = this.restTemplate
                .postForEntity(this.createURLWithPort("/expense"), this.addExpenseEntity, ExpensesList.class);

        String tagName = addExpenseResponse.getBody().getExpenses().get(0).getTags().get(0).getName();
        String searchCriteriaURL = this.createURLWithPort("/expense/criteria", tagName, "", "");

        ResponseEntity<ExpensesList> getExpensesByCriteriaResponse = this.restTemplate.getForEntity(searchCriteriaURL,
                ExpensesList.class);

        assertEquals(HttpStatus.OK, getExpensesByCriteriaResponse.getStatusCode());
        assertNotEquals(0, getExpensesByCriteriaResponse.getBody().getExpenses().size());
    }

    @Test
    void testGetExpensesBySearchCriteriaWithFromDateSettedOnly() {
        this.restTemplate.postForEntity(this.createURLWithPort("/expense"), this.addExpenseEntity, ExpensesList.class);

        String searchCriteriaURL = this.createURLWithPort("/expense/criteria", "", this.fromDate.toString(), "");

        ResponseEntity<ExpensesList> getExpensesByCriteriaResponse = this.restTemplate.getForEntity(searchCriteriaURL,
                ExpensesList.class);

        assertEquals(HttpStatus.OK, getExpensesByCriteriaResponse.getStatusCode());
        assertNotEquals(0, getExpensesByCriteriaResponse.getBody().getExpenses().size());
    }

    @Test
    void testGetExpensesBySearchCriteriaWithToDateSettedOnly() {
        this.restTemplate.postForEntity(this.createURLWithPort("/expense"), this.addExpenseEntity, ExpensesList.class);

        String searchCriteriaURL = this.createURLWithPort("/expense/criteria", "", "", this.toDate.toString());

        ResponseEntity<ExpensesList> getExpensesByCriteriaResponse = this.restTemplate.getForEntity(searchCriteriaURL,
                ExpensesList.class);

        assertEquals(HttpStatus.OK, getExpensesByCriteriaResponse.getStatusCode());
        assertNotEquals(0, getExpensesByCriteriaResponse.getBody().getExpenses().size());
    }

    @Test
    void testGetExpensesBySearchCriteriaWithBothDatesSetted() {
        this.restTemplate.postForEntity(this.createURLWithPort("/expense"), this.addExpenseEntity, ExpensesList.class);

        String searchCriteriaURL = this.createURLWithPort("/expense/criteria", "", this.fromDate.toString(),
                this.toDate.toString());

        ResponseEntity<ExpensesList> getExpensesByCriteriaResponse = this.restTemplate.getForEntity(searchCriteriaURL,
                ExpensesList.class);

        assertEquals(HttpStatus.OK, getExpensesByCriteriaResponse.getStatusCode());
        assertNotEquals(0, getExpensesByCriteriaResponse.getBody().getExpenses().size());
    }

    @Test
    void testGetExpensesBySearchCriteriaWithAllDataSetted() {
        ResponseEntity<ExpensesList> addExpenseResponse = this.restTemplate
                .postForEntity(this.createURLWithPort("/expense"), this.addExpenseEntity, ExpensesList.class);

        String tagName = addExpenseResponse.getBody().getExpenses().get(0).getTags().get(0).getName();
        String searchCriteriaURL = this.createURLWithPort("/expense/criteria", tagName, this.fromDate.toString(),
                this.toDate.toString());

        ResponseEntity<ExpensesList> getExpensesByCriteriaResponse = this.restTemplate.getForEntity(searchCriteriaURL,
                ExpensesList.class);

        assertEquals(HttpStatus.OK, getExpensesByCriteriaResponse.getStatusCode());
        assertNotEquals(0, getExpensesByCriteriaResponse.getBody().getExpenses().size());
    }
}
