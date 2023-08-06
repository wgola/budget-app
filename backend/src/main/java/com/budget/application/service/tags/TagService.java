package com.budget.application.service.tags;

import java.util.Optional;
import java.util.List;

import com.budget.application.model.Tag;

public interface TagService {

    public Optional<Tag> getTagByName(String tagName);

    public Optional<List<Tag>> getAllTags();

    public void deleteTag(Long tagId);

    public Tag createTag(String tagName);
}
