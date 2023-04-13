package com.budget.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.budget.application.model.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    Optional<Tag> findById(Long id);

    List<Tag> findAll();

    List<Tag> findByName(String name);
}
