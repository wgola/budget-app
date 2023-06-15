import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NewExpenseComponent } from "./components/new-expense/new-expense.component";
import { MatCardModule } from "@angular/material/card";
import { ReactiveFormsModule } from "@angular/forms";
import {
  MAT_CHIPS_DEFAULT_OPTIONS,
  MatChipsModule,
} from "@angular/material/chips";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import { MatFormFieldModule } from "@angular/material/form-field";

@NgModule({
  declarations: [AppComponent, NewExpenseComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatCardModule,
    MatChipsModule,
    MatAutocompleteModule,
    MatFormFieldModule,
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
