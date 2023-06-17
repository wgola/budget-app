import { Tag } from "./Tag";

export interface Expense {
  expenseID: number;
  tags: Tag[];
  value: number;
  creationDate: string;
}
