import { Component, OnInit } from '@angular/core';
import { StudentsService } from '../services/students.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-payment-details',
  templateUrl: './payment-details.component.html',
  styleUrl: './payment-details.component.css'
})
export class PaymentDetailsComponent implements OnInit{
  paymentId! : number;
  pdfFileUrl : any;
  showProgress : boolean = false;


  constructor(private studentsService : StudentsService, private route : ActivatedRoute){

  }

  ngOnInit(): void {
    this.showProgress = true;
      this.paymentId = this.route.snapshot.params['id'];
      this.studentsService.getPaymentDetails(this.paymentId).subscribe({
        next:value =>{
          this.showProgress = false;
          console.log(value);

          let blob: Blob = new Blob([value],{type: 'application/pdf'});
          this.pdfFileUrl = window.URL.createObjectURL(blob);
        },
        
        error : err=>{
          this.showProgress = false;
          console.log(err);
        }
      }
    )
  }

  afterLoardComplete(event: any){

  }

}
