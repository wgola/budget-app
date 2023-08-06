package com.budget.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import com.budget.application.model.Expense;
import com.budget.application.response.provider.expenses.ExpensesList;
import com.budget.application.utils.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
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
                .append(port)
                .append(uri);

        return result.toString();
    }

    private String createURLWithPort(String uri, String tagName, String fromDate, String toDate) {
        StringBuilder result = new StringBuilder("http://localhost:")
                .append(port)
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
        Expense expenseToAdd = TestUtils.generateTestExpense(
                1,
                LocalDateTime.of(2018, 11, 12, 1, 0, 0));

        addExpenseEntity = new HttpEntity<>(expenseToAdd, headers);
    }

    @Test
    void testAddExpense() {
        var addExpenseResponse = restTemplate.postForEntity(
                createURLWithPort("/expense"),
                addExpenseEntity,
                ExpensesList.class);

        assertEquals(HttpStatus.CREATED, addExpenseResponse.getStatusCode());
        assertNotEquals(0, addExpenseResponse.getBody().getExpenses().size());
    }

    @Test
    void testGetExpenses() {
        restTemplate.postForEntity(
                createURLWithPort("/expense"),
                addExpenseEntity,
                ExpensesList.class);

        var getExpenseResponse = restTemplate.getForEntity(
                createURLWithPort("/expense"),
                ExpensesList.class);

        assertEquals(HttpStatus.OK, getExpenseResponse.getStatusCode());
        assertNotEquals(0, getExpenseResponse.getBody().getExpenses().size());
    }

    @Test
    void testDeleteExpense() {
        var addExpenseResponse = restTemplate.postForEntity(
                createURLWithPort("/expense"),
                addExpenseEntity,
                ExpensesList.class);

        Long addedExpenseID = addExpenseResponse.getBody()
                .getExpenses()
                .get(0)
                .getExpenseID();

        var deleteExpenseResponse = restTemplate.exchange(
                createURLWithPort("/expense/" + addedExpenseID),
                HttpMethod.DELETE,
                addExpenseResponse,
                ExpensesList.class);

        assertEquals(HttpStatus.OK, deleteExpenseResponse.getStatusCode());
        assertNull(deleteExpenseResponse.getBody().getExpenses());
    }

    @Test
    void testGetExpensesBySearchCriteriaWithTagNamesSettedOnly() {
        var addExpenseResponse = restTemplate.postForEntity(
                createURLWithPort("/expense"),
                addExpenseEntity,
                ExpensesList.class);

        String tagName = addExpenseResponse.getBody()
                .getExpenses()
                .get(0)
                .getTags()
                .get(0)
                .getName();

        String searchCriteriaURL = createURLWithPort(
                "/expense/criteria",
                tagName,
                "",
                "");

        var getExpensesByCriteriaResponse = restTemplate.getForEntity(
                searchCriteriaURL,
                ExpensesList.class);

        assertEquals(HttpStatus.OK, getExpensesByCriteriaResponse.getStatusCode());
        assertNotEquals(0, getExpensesByCriteriaResponse.getBody().getExpenses().size());
    }

    @Test
    void testGetExpensesBySearchCriteriaWithFromDateSettedOnly() {
        restTemplate.postForEntity(
                createURLWithPort("/expense"),
                addExpenseEntity,
                ExpensesList.class);

        String searchCriteriaURL = createURLWithPort(
                "/expense/criteria",
                "",
                fromDate.toString(),
                "");

        var getExpensesByCriteriaResponse = restTemplate.getForEntity(
                searchCriteriaURL,
                ExpensesList.class);

        assertEquals(HttpStatus.OK, getExpensesByCriteriaResponse.getStatusCode());
        assertNotEquals(0, getExpensesByCriteriaResponse.getBody().getExpenses().size());
    }

    @Test
    void testGetExpensesBySearchCriteriaWithToDateSettedOnly() {
        restTemplate.postForEntity(
                createURLWithPort("/expense"),
                addExpenseEntity,
                ExpensesList.class);

        String searchCriteriaURL = createURLWithPort(
                "/expense/criteria",
                "",
                "",
                toDate.toString());

        var getExpensesByCriteriaResponse = restTemplate.getForEntity(
                searchCriteriaURL,
                ExpensesList.class);

        assertEquals(HttpStatus.OK, getExpensesByCriteriaResponse.getStatusCode());
        assertNotEquals(0, getExpensesByCriteriaResponse.getBody().getExpenses().size());
    }

    @Test
    void testGetExpensesBySearchCriteriaWithBothDatesSetted() {
        restTemplate.postForEntity(
                createURLWithPort("/expense"),
                addExpenseEntity,
                ExpensesList.class);

        String searchCriteriaURL = createURLWithPort(
                "/expense/criteria",
                "", fromDate.toString(),
                toDate.toString());

        var getExpensesByCriteriaResponse = restTemplate.getForEntity(
                searchCriteriaURL,
                ExpensesList.class);

        assertEquals(HttpStatus.OK, getExpensesByCriteriaResponse.getStatusCode());
        assertNotEquals(0, getExpensesByCriteriaResponse.getBody().getExpenses().size());
    }

    @Test
    void testGetExpensesBySearchCriteriaWithAllDataSetted() {
        var addExpenseResponse = restTemplate.postForEntity(
                createURLWithPort("/expense"),
                addExpenseEntity,
                ExpensesList.class);

        String tagName = addExpenseResponse.getBody()
                .getExpenses()
                .get(0)
                .getTags()
                .get(0)
                .getName();

        String searchCriteriaURL = createURLWithPort(
                "/expense/criteria",
                tagName,
                fromDate.toString(),
                toDate.toString());

        var getExpensesByCriteriaResponse = restTemplate.getForEntity(
                searchCriteriaURL,
                ExpensesList.class);

        assertEquals(HttpStatus.OK, getExpensesByCriteriaResponse.getStatusCode());
        assertNotEquals(0, getExpensesByCriteriaResponse.getBody().getExpenses().size());
    }
}
