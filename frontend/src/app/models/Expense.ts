import { Tag } from "./Tag";

export interface Expense {
  id: number;
  tags: Tag[];
  value: number;
  date: string;
}
