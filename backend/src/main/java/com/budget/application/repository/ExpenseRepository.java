package com.budget.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.budget.application.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
