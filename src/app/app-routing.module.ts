import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { StudentsComponent } from './students/students.component';
import { PaymentsComponent } from './payments/payments.component';
import { ClassesComponent } from './classes/classes.component';
import { LoadPaymentsComponent } from './load-payments/load-payments.component';
import { LoadStudentsComponent } from './load-students/load-students.component';
import { AdminTemplateComponent } from './admin-template/admin-template.component';
import { AuthGuard } from './guards/auth.guard';
import { AuthorizationGuard } from './guards/authorization.guard';
import { StudentDetailsComponent } from './student-details/student-details.component';
import { NewPaymentComponent } from './new-payment/new-payment.component';
import { PaymentDetailsComponent } from './payment-details/payment-details.component';
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';


const routes: Routes = [
  {path : "", redirectTo : "/login", pathMatch: "full"},
  {path : "login", component : LoginComponent},
  {path : "admin", component : AdminTemplateComponent, canActivate : [AuthGuard],
  
  children : [
    {path : "home", component : HomeComponent},
    {path : "profile", component : ProfileComponent},
    {path : "dashboard", component : DashboardComponent},
    {path : "students", component : StudentsComponent,canActivate : [AuthorizationGuard], data : {roles : ['ADMIN']}},
    {path : "payments", component : PaymentsComponent,canActivate : [AuthorizationGuard], data : {roles : ['ADMIN']}},
    {path : "student-details/:code", component : StudentDetailsComponent},
    {path : ":studentCode/new-payment", component : NewPaymentComponent},
    {path : "classes", component : ClassesComponent},
    {path : "payment-details/:id", component : PaymentDetailsComponent},
    {path : "notAuthorized", component : NotAuthorizedComponent},
    {path : "loadStudents", component : LoadStudentsComponent,canActivate : [AuthorizationGuard], data : {roles : ['ADMIN']}},
    {path : "loadPayments", component : LoadPaymentsComponent,canActivate : [AuthorizationGuard], data : {roles : ['ADMIN']}},

  ]},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
