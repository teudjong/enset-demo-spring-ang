  import { EnvironmentInjector, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdminTemplateComponent } from './admin-template/admin-template.component';
import { MatToolbarModule } from "@angular/material/toolbar";
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListItem, MatNavList} from '@angular/material/list';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { StudentsComponent } from './students/students.component';
import { PaymentsComponent } from './payments/payments.component';
import { ClassesComponent } from './classes/classes.component';
import {MatCardModule} from '@angular/material/card';
import {MatDividerModule} from '@angular/material/divider';
import { LoadStudentsComponent } from './load-students/load-students.component';
import { LoadPaymentsComponent } from './load-payments/load-payments.component';
import { MatTableModule } from '@angular/material/table'
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortHeader, MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthGuard } from './guards/auth.guard';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthorizationGuard } from './guards/authorization.guard';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { StudentDetailsComponent } from './student-details/student-details.component';
import { NewPaymentComponent } from './new-payment/new-payment.component';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule} from '@angular/material/datepicker';
import { MatSelectModule } from '@angular/material/select';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { PaymentDetailsComponent } from './payment-details/payment-details.component';
import { AppHttpInterceptor } from './interceptors/app-http.interceptor';
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';



@NgModule({
  declarations: [
    AppComponent,
    AdminTemplateComponent,
    HomeComponent,
    ProfileComponent,
    LoginComponent,
    DashboardComponent,
    StudentsComponent,
    PaymentsComponent,
    ClassesComponent,
    LoadStudentsComponent,
    LoadPaymentsComponent,
    StudentDetailsComponent,
    NewPaymentComponent,
    PaymentDetailsComponent,
    NotAuthorizedComponent

    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatSidenavModule,
    MatNavList,
    MatListItem,
    MatCardModule,
    MatDividerModule,
    MatTableModule,
    MatPaginatorModule,
    MatSort,
    MatSortHeader,
    MatInputModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatTableModule,
    MatSortModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatSelectModule,
    PdfViewerModule,
    MatProgressSpinnerModule
  ],
  providers: [
    {provide : HTTP_INTERCEPTORS, useClass : AppHttpInterceptor, multi : true},
    AuthGuard,
    AuthorizationGuard
    //provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
