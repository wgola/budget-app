package com.budget.application.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.budget.application.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByName(String name);
}
