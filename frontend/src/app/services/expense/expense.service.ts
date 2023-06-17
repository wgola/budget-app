import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, Subject } from "rxjs";
import { Expense } from "src/app/models/Expense";
import { Tag } from "src/app/models/Tag";

@Injectable({
  providedIn: "root",
})
export class ExpenseService {
  public retrievedExpenseResult: Observable<Expense[]>;
  public deleteExpenseResult: Observable<Expense[]>;
  public addedExpenseResult: Observable<Expense[]>;
  public hostAddress: string = "http://localhost:3000/";
  public subject = new Subject<HttpErrorResponse>();

  constructor(private httpClient: HttpClient) {}

  public deleteExpense(expenseID: number) {
    this.deleteExpenseResult = new Observable((observer) => {
      const url = this.hostAddress + "expense/" + expenseID;

      this.httpClient.delete<any>(url).subscribe(
        (response) => {
          observer.next(response);
        },
        (err) => this.handleException(err)
      );
    });

    return this.deleteExpenseResult;
  }

  public saveExpense(expense: Expense) {
    this.addedExpenseResult = new Observable((observer) => {
      const url = this.hostAddress + "expense";
      const httpOptions = {
        headers: new HttpHeaders({
          "Content-Type": "application/json",
        }),
      };

      if (typeof expense.tags === "string") {
        const tags: string = expense.tags;
        const splittedTags = tags.split(",");
        expense.tags = splittedTags.map((el) => {
          return { name: el };
        });
      }

      const objectToSend = expense.expenseID
        ? {
            expenseID: expense.expenseID,
            value: expense.value,
            tags: expense.tags,
            formattedDate: expense.creationDate,
          }
        : {
            tags: expense.tags,
            value: expense.value,
          };

      console.log(objectToSend);

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
    this.retrievedExpenseResult = new Observable((observer) => {
      const url = this.hostAddress + "expense";

      this.httpClient.get<{ expenses: Expense[] }>(url).subscribe(
        (response) => {
          const expenses = response.expenses;
          observer.next(expenses);
        },
        (err) => this.handleException(err)
      );
    });

    return this.retrievedExpenseResult;
  }

  public getExpenseFromData(tags: Tag[], value: number): Expense {
    const expenseDate = new Date().toISOString();
    const expense = { expenseID: 0, tags, value, creationDate: expenseDate };

    console.log(expense);

    return expense;
  }

  public addExpense(expense: Expense) {
    return this.saveExpense(expense);
  }
}
