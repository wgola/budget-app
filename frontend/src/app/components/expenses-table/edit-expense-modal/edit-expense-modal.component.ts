import { Component, Inject, OnInit } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { Expense } from "src/app/models/Expense";
import { ExpenseEditOperation } from "src/app/models/ExpenseEditOperation";
import { ExpenseService } from "src/app/services/expense/expense.service";

@Component({
  selector: "app-edit-expense-modal",
  templateUrl: "./edit-expense-modal.component.html",
  styleUrls: ["./edit-expense-modal.component.scss"],
})
export class EditExpenseModalComponent implements OnInit {
  public operationType: "EDIT" | "DELETE";
  public editForm: FormGroup;
  public startingExpenseData: Expense;
  public editedForm: any;

  public get dateCtrl(): FormControl {
    return this.editForm.get("creationDate") as FormControl;
  }

  public get tagsCtrl(): FormControl {
    return this.editForm.get("tags") as FormControl;
  }

  public get valueCtrl(): FormControl {
    return this.editForm.get("value") as FormControl;
  }

  constructor(
    public dialogRef: MatDialogRef<EditExpenseModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ExpenseEditOperation,
    private expenseService: ExpenseService
  ) {
    this.startingExpenseData = data.expense;
    this.operationType = data.operationType;

    this.editForm = new FormGroup({
      creationDate: new FormControl(
        data.expense.creationDate,
        Validators.required
      ),
      value: new FormControl(data.expense.value, [
        Validators.required,
        Validators.pattern("^(?:0|[1-9][0-9]*).[0-9]+$"),
      ]),
      tags: new FormControl(
        data.expense.tags.map((el) => el.name),
        Validators.required
      ),
    });
  }

  ngOnInit(): void {
    const { expenseID, ...rest } = this.startingExpenseData;
    this.editedForm = rest;

    this.editForm.valueChanges.subscribe((response) => {
      this.editedForm = response;
    });
  }

  public deleteExpenseClickHandler() {
    this.expenseService
      .deleteExpense(this.startingExpenseData.expenseID)
      .subscribe((response) => {
        this.dialogRef.close();
      });
  }

  public cancelClickHandler() {
    this.dialogRef.close();
  }

  public submitChangesButton() {
    const expenseToSubmit: Expense = {
      ...this.editedForm,
      expenseID: this.startingExpenseData.expenseID,
      creationDate:
        typeof this.editedForm.creationDate === "object"
          ? this.editedForm.creationDate.toISOString()
          : this.editedForm.creationDate,
    };

    this.expenseService.saveExpense(expenseToSubmit).subscribe((response) => {
      this.dialogRef.close();
    });
  }
}
