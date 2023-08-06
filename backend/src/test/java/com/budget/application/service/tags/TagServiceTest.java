package com.budget.application.service.tags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.budget.application.model.Tag;
import com.budget.application.repository.TagRepository;
import com.budget.application.utils.TestUtils;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    private TagService tagService;
    private List<Tag> allGeneratedTestTags;
    private String newTagName;

    @BeforeEach
    void setUp() {
        allGeneratedTestTags = TestUtils.generateTestTags(10);

        Tag generatedTag = allGeneratedTestTags.get(0);
        newTagName = generatedTag.getName();

        Mockito.when(tagRepository.save(Mockito.any(Tag.class))).thenReturn(generatedTag);
        Mockito.when(tagRepository.findAll()).thenReturn(allGeneratedTestTags);
        Mockito.when(tagRepository.findByName(newTagName)).thenReturn(Arrays.asList(generatedTag));

        tagService = new TagServiceImpl(tagRepository);
    }

    @Test
    void testCreateTag() {
        Tag createdTag = tagService.createTag(newTagName);

        assertNotNull(createdTag);
        assertEquals(createdTag.getName(), newTagName);
    }

    @Test
    void testDeleteTag() {
        Tag tagToDelete = tagService.getAllTags()
                .get()
                .get(0);

        tagService.deleteTag(tagToDelete.getTagID());
        Optional<Tag> deletedTag = tagRepository.findById(tagToDelete.getTagID());

        assertTrue(deletedTag.isEmpty());
    }

    @Test
    void testGetAllTags() {
        List<Tag> allTags = tagService.getAllTags().get();

        assertEquals(allTags.size(), allGeneratedTestTags.size());
        assertEquals(allTags, allGeneratedTestTags);
    }

    @Test
    void testGetTagByName() {
        Tag retrievedTagByName = tagService.getTagByName(newTagName).get();

        assertEquals(retrievedTagByName.getName(), newTagName);
    }
}
