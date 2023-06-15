import { Component, Inject } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: "app-added-expense-modal",
  templateUrl: "./added-expense-modal.component.html",
  styleUrls: ["./added-expense-modal.component.scss"],
})
export class AddedExpenseModalComponent {
  constructor(
    public dialogRef: MatDialogRef<AddedExpenseModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  public okClick() {
    this.dialogRef.close();
  }
}
