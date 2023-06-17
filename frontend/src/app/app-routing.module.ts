import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { NewExpenseComponent } from "./components/new-expense/new-expense.component";
import { ExpensesTableComponent } from "./components/expenses-table/expenses-table.component";

const routes: Routes = [
  {
    path: "",
    component: NewExpenseComponent,
  },
  { path: "expenses-table", component: ExpensesTableComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
