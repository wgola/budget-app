import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import {
  BrowserAnimationsModule,
  NoopAnimationsModule,
} from "@angular/platform-browser/animations";
import { NewExpenseComponent } from "./components/new-expense/new-expense.component";
import { MatCardModule } from "@angular/material/card";
import { ReactiveFormsModule } from "@angular/forms";
import {
  MAT_CHIPS_DEFAULT_OPTIONS,
  MatChipsModule,
} from "@angular/material/chips";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatDialogModule } from "@angular/material/dialog";
import { AddedExpenseModalComponent } from "./components/new-expense/added-expense-modal/added-expense-modal.component";

@NgModule({
  declarations: [AppComponent, NewExpenseComponent, AddedExpenseModalComponent],
  imports: [
    MatDialogModule,
    MatFormFieldModule,
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    MatCardModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatInputModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatChipsModule,
    MatIconModule,
  ],
  providers: [
    {
      provide: MAT_CHIPS_DEFAULT_OPTIONS,
      useValue: { separatorKeyCodes: [13, 188] },
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
