package com.budget.application.response.provider;

import java.util.List;

import com.budget.application.model.Tag;

public class TagsList {

    private List<Tag> tags;

    public TagsList() {
    }

    public TagsList(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
