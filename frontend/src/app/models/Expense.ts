import { Tag } from "./Tag";

export interface Expense {
  tags: Tag[];
  value: number;
  date: string;
}
