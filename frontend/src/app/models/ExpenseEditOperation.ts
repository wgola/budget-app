import { Expense } from "./Expense";

export interface ExpenseEditOperation {
  operationType: "EDIT" | "DELETE";
  expense: Expense;
}
