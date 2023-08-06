package com.budget.application.response.provider.tags;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.budget.application.model.Tag;
import com.budget.application.service.tags.TagService;

@Service
public class TagResponseProvider {

    private final TagService tagService;

    public TagResponseProvider(TagService tagService) {
        this.tagService = tagService;
    }

    public ResponseEntity<TagsList> getAllTags() {
        try {
            List<Tag> retrievedTags = tagService.getAllTags().get();
            TagsList body = new TagsList(retrievedTags);

            return new ResponseEntity<TagsList>(body, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<TagsList>(new TagsList(), HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<TagsList> createTag(String tagName) {
        try {
            Tag createdTag = tagService.createTag(tagName);
            TagsList body = new TagsList(Arrays.asList(createdTag));

            return new ResponseEntity<TagsList>(body, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<TagsList>(new TagsList(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public ResponseEntity<TagsList> deleteTag(Long tagID) {
        try {
            tagService.deleteTag(tagID);

            return new ResponseEntity<TagsList>(new TagsList(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<TagsList>(new TagsList(), HttpStatus.BAD_REQUEST);
        }
    }
}
