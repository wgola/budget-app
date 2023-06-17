import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { Expense } from "src/app/models/Expense";
import { Tag } from "src/app/models/Tag";

@Injectable({
  providedIn: "root",
})
export class ExpenseService {
  constructor() {}

  public deleteExpense(expenseID: number) {
    return of([]);
  }

  public saveExpense(expense: Expense) {
    return of([]);
  }

  public getAllExpenses(): Observable<Expense[]> {
    const expense1 = {
      id: 0,
      tags: [{ name: "tag1" }],
      value: 20.5,
      date: "2020-05-10",
    };
    const expense2 = {
      id: 1,
      tags: [{ name: "tag2" }],
      value: 23.5,
      date: "2020-05-10",
    };

    return of([expense1, expense2]);
  }

  public getExpenseFromData(tags: Tag[], value: number): Expense {
    const expenseDate = new Date().toISOString();
    const expense = { id: 0, tags, value, date: expenseDate };

    return expense;
  }

  public addExpense(expense: Expense): Observable<Expense> {
    return of(expense);
  }
}
