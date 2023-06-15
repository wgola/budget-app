import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { Expense } from "src/app/models/Expense";
import { Tag } from "src/app/models/Tag";

@Injectable({
  providedIn: "root",
})
export class ExpenseService {
  constructor() {}

  public getExpenseFromData(tags: Tag[], value: number): Expense {
    const expenseDate = new Date().toISOString();
    const expense = { tags, value, date: expenseDate };

    return expense;
  }

  public addExpense(expense: Expense): Observable<Expense> {
    return of(expense);
  }
}
