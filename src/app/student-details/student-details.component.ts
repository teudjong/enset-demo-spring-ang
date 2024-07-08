import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { StudentsService } from '../services/students.service';
import { Payment } from '../model/students.model';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-student-details',
  templateUrl: './student-details.component.html',
  styleUrl: './student-details.component.css'
})
export class StudentDetailsComponent implements OnInit{
studentCode! : string;
studentPayments! : Array<Payment>;
paymentsDataSource! : MatTableDataSource<Payment>;
public displayedColumns = ['id','date','amount','type','status','firstName','details'];
showProgress : boolean = false;


  constructor(private activatedRoute : ActivatedRoute,
              private studentsService : StudentsService,
            private router : Router)
  {

  }

  ngOnInit(): void {
      this.studentCode = this.activatedRoute.snapshot.params['code'];
      this.showProgress = true;
      this.studentsService.getStudentPayments(this.studentCode).subscribe({
        next : value => {
          this.showProgress = false;
            this.studentPayments = value;
            this.paymentsDataSource = new MatTableDataSource<Payment>(this.studentPayments)
        },
        error : err =>{
          this.showProgress = false;
          console.log(err);
        }
      });
  }

  newPayment(){
    console.log(this.studentCode)
     this.router.navigateByUrl(`/admin/${this.studentCode}/new-payment`)
  }

  paymentDetails(payment: Payment){
    this.router.navigateByUrl(`/admin/payment-details/${payment.id}`);
  }
}
