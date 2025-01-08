
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { SignupComponent } from './signup/signup.component';
import { MainborderComponent } from './mainborder/mainborder.component';
import { BookroomComponent } from './bookroom/bookroom.component';
import { DropdownComponent } from './dropdown/dropdown.component';
import { AdminbookroomComponent } from './adminbookroom/adminbookroom.component';





export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'admin-login', component: AdminLoginComponent },
  { path: 'admin-dashboard', component: AdminDashboardComponent },
  { path: 'signup', component: SignupComponent }, 
  {path: 'emp-dashboard', component: MainborderComponent},
  { path: 'dropdown', component: DropdownComponent },
  { path: 'bookingg', component: BookroomComponent },
  {path : 'adminbookingg', component: AdminbookroomComponent},


];



@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })

export class AppRoutingModule { }
