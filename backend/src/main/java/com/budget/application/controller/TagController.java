package com.budget.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.budget.application.response.provider.TagResponseProvider;
import com.budget.application.response.provider.TagsList;

@RestController
public class TagController {

    @Autowired
    private TagResponseProvider tagResponseProvider;

    @CrossOrigin
    @GetMapping(value = "/tag")
    public ResponseEntity<TagsList> getAllTags() {
        return this.tagResponseProvider.getAllTags();
    }

    @CrossOrigin
    @PostMapping(value = "/tag")
    public ResponseEntity<TagsList> addNewTag(@RequestBody String tagName) {
        return this.tagResponseProvider.createTag(tagName);
    }

    @CrossOrigin
    @DeleteMapping(value = "/tag/{tagID}")
    public ResponseEntity<TagsList> deleteTag(@PathVariable Long tagID) {
        return this.tagResponseProvider.deleteTag(tagID);
    }
}