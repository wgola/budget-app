package com.budget.application.service.tags;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.budget.application.model.Tag;
import com.budget.application.repository.TagRepository;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag createTag(String tagName) {
        List<Tag> foundByName = tagRepository.findByName(tagName);

        if (foundByName.isEmpty()) {
            Tag newTag = new Tag(tagName);

            return tagRepository.save(newTag);
        }

        return foundByName.get(0);
    }

    @Override
    public Optional<List<Tag>> getAllTags() {
        List<Tag> allTags = tagRepository.findAll();

        return Optional.of(allTags);
    }

    @Override
    public void deleteTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }

    @Override
    public Optional<Tag> getTagByName(String tagName) {
        List<Tag> foundByName = tagRepository.findByName(tagName);
        Tag foundTag = foundByName.size() > 0 ? foundByName.get(0) : null;

        return Optional.of(foundTag);
    }
}
