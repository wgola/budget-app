package com.budget.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.budget.application.model.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {

    Optional<Tag> findById(Long id);

    List<Tag> findAll();

    List<Tag> findByName(String name);
}
