package com.budget.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budget.application.response.provider.tags.TagResponseProvider;
import com.budget.application.response.provider.tags.TagsList;

@RestController
@CrossOrigin
@RequestMapping("/tag")
public class TagController {

    private final TagResponseProvider tagResponseProvider;

    public TagController(TagResponseProvider tagResponseProvider) {
        this.tagResponseProvider = tagResponseProvider;
    }

    @GetMapping()
    public ResponseEntity<TagsList> getAllTags() {
        return tagResponseProvider.getAllTags();
    }

    @PostMapping()
    public ResponseEntity<TagsList> addNewTag(@RequestBody String tagName) {
        return tagResponseProvider.createTag(tagName);
    }

    @DeleteMapping("/{tagID}")
    public ResponseEntity<TagsList> deleteTag(@PathVariable Long tagID) {
        return tagResponseProvider.deleteTag(tagID);
    }
}
