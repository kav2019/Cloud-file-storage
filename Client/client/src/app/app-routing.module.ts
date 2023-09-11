import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {IndexComponent} from "./layout/index/index.component";
import {LoginComponent} from "./auth/login/login.component";


const routes: Routes = [
  {path: 'login', component:LoginComponent},
  {path: 'index', component: IndexComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
