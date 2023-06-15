import { Component, ElementRef, OnInit, ViewChild } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import {
  MatAutocomplete,
  MatAutocompleteSelectedEvent,
} from "@angular/material/autocomplete";
import { Observable, map } from "rxjs";
import { Tag } from "src/app/models/Tag";
import { TagService } from "src/app/services/tag/tag.service";

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

  public expenseForm = new FormGroup({
    tags: new FormControl({
      value: undefined,
      disabled: this.selectedTags.length >= 5,
    }),
    value: new FormControl(undefined, Validators.required),
  });

  public get tagsControl(): FormControl {
    return this.expenseForm.get("tags") as FormControl;
  }

  public get valueControl(): FormControl {
    return this.expenseForm.get("value") as FormControl;
  }

  constructor(private tagService: TagService) {}

  ngOnInit(): void {
    this.tagService
      .getAllTags()
      .subscribe((response) => (this.allTags = response));

    this.filteredTags = this.tagsControl.valueChanges.pipe(
      map((val: any | null) =>
        val ? this.filterTags(val) : this.allTags.slice()
      )
    );
  }

  private filterTags(tag: any): Tag[] {
    const filterValue =
      typeof tag === "object" ? tag.name.toLowerCase() : tag.toLowerCase();

    return this.allTags.filter((val) =>
      val.name.toLowerCase().includes(filterValue)
    );
  }

  public add(event: any) {
    console.log("add invoked");
  }

  public addExpenseClickHandler() {
    console.log("addExpenseClickHandler invoked");
  }

  public selected(event: MatAutocompleteSelectedEvent) {
    const selectedTag = event.option.value;
    this.selectedTags.push(selectedTag);
    this.tagsInput.nativeElement.value = "";
  }

  public submitExpense(event: any) {
    console.log("submit expense invoked");
  }
}
