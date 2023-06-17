import { Component, OnInit } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { Expense } from "src/app/models/Expense";
import { Tag } from "src/app/models/Tag";
import { ExpenseService } from "src/app/services/expense/expense.service";
import { EditExpenseModalComponent } from "./edit-expense-modal/edit-expense-modal.component";

@Component({
  selector: "app-expenses-table",
  templateUrl: "./expenses-table.component.html",
  styleUrls: ["./expenses-table.component.scss"],
})
export class ExpensesTableComponent implements OnInit {
  public displayedColumns: string[] = ["id", "date", "tags", "value", "crud"];
  public allExpenses: Expense[];

  constructor(
    private expenseService: ExpenseService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.expenseService
      .getAllExpenses()
      .subscribe((response) => (this.allExpenses = response));
  }

  public mapTags(param: Tag[]) {
    if (param) {
      const tagsList: String[] = param.map((el) => el.name);
      return tagsList.join(", ");
    }

    return [];
  }

  public openDialog(element: Expense, operationType: "EDIT" | "DELETE") {
    const expenseEditOperation = {
      operationType,
      expense: element,
    };

    const dialogRef = this.dialog.open(EditExpenseModalComponent, {
      width: "350px",
      data: expenseEditOperation,
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log("the dialog was closed");
    });
  }
}
