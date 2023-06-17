import { Tag } from "./Tag";

export interface Expense {
  id: number;
  tags: Tag[] | string;
  value: number;
  date: string;
}
