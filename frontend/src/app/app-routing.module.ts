import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { NewExpenseComponent } from "./components/new-expense/new-expense.component";

const routes: Routes = [
  {
    path: "",
    component: NewExpenseComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
