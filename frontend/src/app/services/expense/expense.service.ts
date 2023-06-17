import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, Subject, of } from "rxjs";
import { Expense } from "src/app/models/Expense";
import { Tag } from "src/app/models/Tag";

@Injectable({
  providedIn: "root",
})
export class ExpenseService {
  public addedExpenseResult: Observable<Expense[]>;
  public hostAddress: string = "http://localhost:3000/";
  public subject = new Subject<HttpErrorResponse>();

  constructor(private httpClient: HttpClient) {}

  public deleteExpense(expenseID: number) {
    return of([]);
  }

  public saveExpense(expense: Expense) {
    this.addedExpenseResult = new Observable((observer) => {
      const url = this.hostAddress + "expense";
      const httpOptions = {
        headers: new HttpHeaders({
          "Content-Type": "application/json",
        }),
      };

      const tagNames: string[] =
        typeof expense.tags === "string"
          ? expense.tags.split(",")
          : expense.tags.map((el) => el.name);

      const objectToSend = expense.id
        ? {
            id: expense.id,
            value: expense.value,
            tags: tagNames,
            formattedDate: expense.date,
          }
        : { tags: tagNames, value: expense.value };

      this.httpClient.post<any>(url, objectToSend, httpOptions).subscribe(
        (response) => {
          observer.next(response);
        },
        (err) => this.handleException(err)
      );
    });

    return this.addedExpenseResult;
  }

  public handleException(err: HttpErrorResponse) {
    this.subject.next(err);
  }

  public onErrorOccurrs(): Observable<HttpErrorResponse> {
    return this.subject.asObservable();
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

  public addExpense(expense: Expense) {
    return this.saveExpense(expense);
  }
}
