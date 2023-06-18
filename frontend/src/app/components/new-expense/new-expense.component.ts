import { Component, ElementRef, OnInit, ViewChild } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import {
  MatAutocomplete,
  MatAutocompleteSelectedEvent,
} from "@angular/material/autocomplete";
import { MatChipInputEvent } from "@angular/material/chips";
import { MatDialog } from "@angular/material/dialog";
import { Observable, Subscription, map } from "rxjs";
import { Tag } from "src/app/models/Tag";
import { ExpenseService } from "src/app/services/expense/expense.service";
import { TagService } from "src/app/services/tag/tag.service";
import { AddedExpenseModalComponent } from "./added-expense-modal/added-expense-modal.component";
import { HttpErrorResponse } from "@angular/common/http";
import { AlertComponent } from "../common/alert/alert.component";

@Component({
  selector: "app-new-expense",
  templateUrl: "./new-expense.component.html",
  styleUrls: ["./new-expense.component.scss"],
})
export class NewExpenseComponent implements OnInit {
  @ViewChild("tagsInput", { static: true })
  tagsInput: ElementRef<HTMLInputElement>;
  @ViewChild("auto", { static: true })
  auto: MatAutocomplete;

  public readonly separatorKeyCodes: number[] = [13, 188];
  public addOnBlur = true;
  public selectedTags: Tag[] = [];
  public allTags: Tag[] = [];
  public filteredTags: Observable<Tag[]>;
  public removableChip: boolean = true;
  public tagServiceErrorSubscription: Subscription;
  public expenseServiceErrorSubscription: Subscription;

  public expenseForm = new FormGroup({
    tags: new FormControl(undefined),
    value: new FormControl(undefined, [
      Validators.required,
      Validators.pattern("^(?:0|[1-9][0-9]*).[0-9]+$"),
    ]),
  });

  public get tagsControl(): FormControl {
    return this.expenseForm.get("tags") as FormControl;
  }

  public get valueControl(): FormControl {
    return this.expenseForm.get("value") as FormControl;
  }

  constructor(
    private tagService: TagService,
    private expenseService: ExpenseService,
    public dialog: MatDialog,
    public alertDialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.tagServiceErrorSubscription = this.tagService
      .onErrorOccurrs()
      .subscribe((error) => {
        this.showAlertModal(error);
      });

    this.expenseServiceErrorSubscription = this.expenseService
      .onErrorOccurrs()
      .subscribe((error) => {
        this.showAlertModal(error);
      });

    this.tagService
      .getAllTags()
      .subscribe((response) => (this.allTags = response));

    this.filteredTags = this.tagsControl.valueChanges.pipe(
      map((val: string | null) =>
        val ? this.filterTags(val) : this.allTags.slice()
      )
    );
  }

  private showAlertModal(error: HttpErrorResponse) {
    const alertDialogRef = this.alertDialog.open(AlertComponent, {
      width: "350px",
      data: error,
    });
  }

  private filterTags(tag: string): Tag[] {
    const filterValue = tag.toLowerCase();

    return this.allTags.filter((val) =>
      val.name.toLowerCase().includes(filterValue)
    );
  }

  public remove(tagToRemove: Tag) {
    this.selectedTags = this.selectedTags.filter(
      (tag) => tag.name !== tagToRemove.name
    );
  }

  public add(event: MatChipInputEvent) {
    const selectedTag = event.value;

    if (selectedTag !== "") {
      const trimmedTag = selectedTag.trim();
      if (
        this.selectedTags.filter((tag) => tag.name === trimmedTag).length === 0
      ) {
        this.selectedTags.push({ name: trimmedTag });
        this.checkIfDisable();
      }
      this.tagsInput.nativeElement.value = "";
    }
  }

  public addExpenseClickHandler() {
    const expenseToAdd = {
      tags: this.selectedTags,
      value: this.valueControl.value,
    };

    this.expenseService.addExpense(expenseToAdd).subscribe((response) => {
      this.showAddExpenseModal();
    });
  }

  private showAddExpenseModal() {
    const dialogRef = this.dialog.open(AddedExpenseModalComponent, {
      width: "350px",
      data: {
        label: "Success",
        content: "Expense added succesfully",
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log("The dialog was closed");
    });
  }

  public selected(event: MatAutocompleteSelectedEvent) {
    const selectedTag = event.option.value;
    if (
      this.selectedTags.filter((tag) => tag.name === selectedTag.name)
        .length === 0
    ) {
      this.selectedTags.push(selectedTag);
      this.checkIfDisable();
    }
    this.tagsInput.nativeElement.value = "";
  }

  private checkIfDisable() {
    if (this.selectedTags.length >= 5) this.tagsControl.disable();
  }
}
