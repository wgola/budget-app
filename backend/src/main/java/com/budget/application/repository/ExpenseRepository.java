package com.budget.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.budget.application.model.Expense;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    Optional<Expense> findById(long id);

    List<Expense> findAll();
}
