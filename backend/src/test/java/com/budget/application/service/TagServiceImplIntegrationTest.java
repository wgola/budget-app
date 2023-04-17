package com.budget.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.budget.application.model.Tag;
import com.budget.application.repository.TagRepository;
import com.budget.application.utils.TestUtils;

@SpringBootTest
class TagServiceImplIntegrationTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    private String tagName;

    @BeforeEach
    void setUp() {
        this.tagName = TestUtils.getRandomTextFromUUID();

        for (int i = 0; i < 3; i++) {
            this.tagService.createTag(TestUtils.getRandomTextFromUUID());
        }
    }

    @AfterEach
    void tearDown() {
        this.tagRepository.deleteAll();
    }

    @Test
    void testCreateTag() {
        List<Tag> tagsBeforeInsertion = this.tagService.getAllTags().get();
        Tag createdTag = this.tagService.createTag(this.tagName);
        List<Tag> tagsAfterInsertion = this.tagService.getAllTags().get();

        assertEquals(createdTag.getName(), this.tagName);
        assertEquals(tagsAfterInsertion.size(), tagsBeforeInsertion.size() + 1);
    }

    @Test
    void testDeleteTag() {
        Long retrievedTagID = this.tagService.getAllTags().get().get(0).getTagID();
        this.tagService.deleteTag(retrievedTagID);
        Optional<Tag> tagFoundByID = this.tagRepository.findById(retrievedTagID);

        assertTrue(tagFoundByID.isEmpty());
    }

    @Test
    void testGetAllTags() {
        List<Tag> retrievedTags = this.tagService.getAllTags().get();

        assertEquals(3, retrievedTags.size());
    }

    @Test
    void testGetTagByName() {
        String retrievedTagName = this.tagService.getAllTags().get().get(0).getName();
        Optional<Tag> foundTag = this.tagService.getTagByName(retrievedTagName);

        assertTrue(foundTag.isPresent());
        assertEquals(foundTag.get().getName(), retrievedTagName);
    }
}
