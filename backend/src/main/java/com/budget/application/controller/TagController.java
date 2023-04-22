package com.budget.application.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    @GetMapping(value = "/tag")
    public Object getAllTags() {

        return null;
    }

    @PostMapping(value = "/tag")
    public Object addNewTag(@RequestBody String name) {

        return null;
    }

    @DeleteMapping(value = "/tag/{tagID}")
    public Object deleteTag(@PathVariable("tagID") Long tagID) {

        return null;
    }
}