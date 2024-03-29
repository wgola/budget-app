package com.budget.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

import com.budget.application.model.Tag;
import com.budget.application.response.provider.tags.TagsList;
import com.budget.application.utils.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
class TagControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    void testAddNewTag() {
        Tag tagToAdd = TestUtils.generateTestTags(1).get(0);

        var addTagEntity = new HttpEntity<>(tagToAdd.getName(), headers);

        var addTagResponse = restTemplate.postForEntity(
                createURLWithPort("/tag"),
                addTagEntity,
                TagsList.class);

        assertEquals(HttpStatus.CREATED, addTagResponse.getStatusCode());
        assertNotEquals(0, addTagResponse.getBody().getTags().size());
    }

    @Test
    void testDeleteTag() {
        Tag tagToAdd = TestUtils.generateTestTags(1).get(0);

        var addTagEntity = new HttpEntity<>(tagToAdd.getName(), headers);
        var addTagResponse = restTemplate.postForEntity(
                createURLWithPort("/tag"),
                addTagEntity,
                TagsList.class);

        Long addedTagID = addTagResponse.getBody()
                .getTags()
                .get(0)
                .getTagID();

        var deleteTagResponse = restTemplate.exchange(
                createURLWithPort("/tag/" + addedTagID),
                HttpMethod.DELETE,
                addTagEntity,
                TagsList.class);

        assertEquals(HttpStatus.OK, deleteTagResponse.getStatusCode());
        assertNull(deleteTagResponse.getBody().getTags());
    }

    @Test
    void testGetTags() {
        var getTagsResponse = restTemplate.getForEntity(
                createURLWithPort("/tag"),
                TagsList.class);

        assertEquals(HttpStatus.OK, getTagsResponse.getStatusCode());
        assertNotNull(getTagsResponse.getBody().getTags());
    }
}
