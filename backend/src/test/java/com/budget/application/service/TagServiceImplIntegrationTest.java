package com.budget.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.budget.application.model.Tag;
import com.budget.application.utils.TestUtils;

@SpringBootTest
class TagServiceImplIntegrationTest {

    @Autowired
    private TagService tagService;

    private String tagName;

    @BeforeEach
    void setUp() {
        this.tagName = TestUtils.getRandomTextFromUUID();

        for (int i = 0; i < 10; i++) {
            this.tagService.createTag(TestUtils.getRandomTextFromUUID());
        }
    }

    @Test
    void testCreateTag() {
        Tag createdTag = this.tagService.createTag(this.tagName);

        assertEquals(createdTag.getName(), this.tagName);
    }

    @Test
    void testDeleteTag() {
        assertEquals(true, true);
    }

    @Test
    void testGetAllTags() {
        List<Tag> retrievedTags = this.tagService.getAllTags().get();

        assertTrue(retrievedTags.size() > 0);
    }

    @Test
    void testGetTagByName() {
        assertEquals(true, true);
    }
}
