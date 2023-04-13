package com.budget.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budget.application.model.Tag;
import com.budget.application.repository.TagRepository;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag createTag(String tagName) {
        Tag newTag = new Tag(tagName);

        return this.tagRepository.save(newTag);
    }

    @Override
    public Optional<List<Tag>> getAllTags() {
        List<Tag> allTags = this.tagRepository.findAll();

        return Optional.of(allTags);
    }

    @Override
    public void deleteTag(Long tagId) {
        this.tagRepository.deleteById(tagId);
    }

    @Override
    public Optional<Tag> getTagByName(String tagName) {
        List<Tag> foundByName = this.tagRepository.findByName(tagName);
        Tag foundTag = foundByName.size() > 0 ? foundByName.get(0) : null;

        return Optional.of(foundTag);
    }
}
