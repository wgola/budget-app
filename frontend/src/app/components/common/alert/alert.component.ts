import { HttpErrorResponse } from "@angular/common/http";
import { Component, Inject, OnInit } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";

@Component({
  selector: "app-alert",
  templateUrl: "./alert.component.html",
  styleUrls: ["./alert.component.scss"],
})
export class AlertComponent implements OnInit {
  public errorDisplayData: string;

  constructor(
    public dialogRef: MatDialogRef<HttpErrorResponse>,
    @Inject(MAT_DIALOG_DATA) public data: HttpErrorResponse
  ) {}

  ngOnInit(): void {
    this.errorDisplayData = JSON.stringify(this.data);
  }

  public okClick() {
    this.dialogRef.close();
  }
}
