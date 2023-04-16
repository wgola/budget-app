package com.budget.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.budget.application.model.Tag;
import com.budget.application.repository.TagRepository;
import com.budget.application.utils.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagRepository tagRepository;

    private List<Tag> allGeneratedTestTags;

    private String newTagName;

    @BeforeEach
    void setUp() {
        this.allGeneratedTestTags = TestUtils.generateTestTags(10);

        Tag generatedTag = this.allGeneratedTestTags.get(0);
        this.newTagName = generatedTag.getName();

        Mockito.when(tagRepository.save(Mockito.any(Tag.class))).thenReturn(generatedTag);
        Mockito.when(tagRepository.findAll()).thenReturn(this.allGeneratedTestTags);
        Mockito.when(tagRepository.findByName(this.newTagName)).thenReturn(Arrays.asList(generatedTag));
    }

    @Test
    void testCreateTag() {
        Tag createdTag = this.tagService.createTag(this.newTagName);

        assertNotNull(createdTag);
        assertEquals(createdTag.getName(), this.newTagName);
    }

    @Test
    void testDeleteTag() {
        Tag tagToDelete = this.tagService.getAllTags().get().get(0);

        this.tagService.deleteTag(tagToDelete.getTagID());
        Optional<Tag> deletedTag = this.tagRepository.findById(tagToDelete.getTagID());

        assertTrue(deletedTag.isEmpty());
    }

    @Test
    void testGetAllTags() {
        List<Tag> allTags = this.tagService.getAllTags().get();

        assertEquals(allTags.size(), this.allGeneratedTestTags.size());
        assertEquals(allTags, this.allGeneratedTestTags);
    }

    @Test
    void testGetTagByName() {
        Tag retrievedTagByName = this.tagService.getTagByName(this.newTagName).get();

        assertEquals(retrievedTagByName.getName(), this.newTagName);
    }
}
