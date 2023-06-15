import { Component } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";

@Component({
  selector: "app-new-expense",
  templateUrl: "./new-expense.component.html",
  styleUrls: ["./new-expense.component.scss"],
})
export class NewExpenseComponent {
  public expenseForm = new FormGroup({
    tags: new FormControl(undefined),
    value: new FormControl(undefined, Validators.required),
  });

  public get tagsControl(): FormControl {
    return this.expenseForm.get("tags") as FormControl;
  }

  public get valueControl(): FormControl {
    return this.expenseForm.get("value") as FormControl;
  }

  constructor() {}

  public submitExpense(event: any) {
    console.log("submit expense invoked");
  }
}
