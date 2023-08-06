package com.budget.application.service.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.budget.application.model.Tag;
import com.budget.application.repository.TagRepository;
import com.budget.application.utils.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class TagServiceIntegrationTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    private String tagName;

    @BeforeEach
    void setUp() {
        tagName = TestUtils.getRandomTextFromUUID();

        for (int i = 0; i < 3; i++) {
            tagRepository.save(new Tag(TestUtils.getRandomTextFromUUID()));
        }
    }

    @AfterEach
    void tearDown() {
        tagRepository.deleteAll();
    }

    @Test
    void testCreateTag() {
        List<Tag> tagsBeforeInsertion = tagService.getAllTags().get();
        Tag createdTag = tagService.createTag(this.tagName);
        List<Tag> tagsAfterInsertion = tagService.getAllTags().get();

        assertEquals(createdTag.getName(), tagName);
        assertEquals(tagsAfterInsertion.size(), tagsBeforeInsertion.size() + 1);
    }

    @Test
    void testDeleteTag() {
        Long retrievedTagID = tagService.getAllTags()
                .get()
                .get(0)
                .getTagID();

        tagService.deleteTag(retrievedTagID);
        Optional<Tag> tagFoundByID = tagRepository.findById(retrievedTagID);

        assertTrue(tagFoundByID.isEmpty());
    }

    @Test
    void testGetAllTags() {
        List<Tag> retrievedTags = tagService.getAllTags().get();

        assertEquals(3, retrievedTags.size());
    }

    @Test
    void testGetTagByName() {
        String retrievedTagName = tagService.getAllTags()
                .get()
                .get(0)
                .getName();

        Optional<Tag> foundTag = tagService.getTagByName(retrievedTagName);

        assertTrue(foundTag.isPresent());
        assertEquals(foundTag.get().getName(), retrievedTagName);
    }
}
