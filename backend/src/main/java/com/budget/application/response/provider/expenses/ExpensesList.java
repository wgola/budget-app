package com.budget.application.response.provider.expenses;

import java.util.List;

import com.budget.application.model.Expense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesList {

    private List<Expense> expenses;
}
