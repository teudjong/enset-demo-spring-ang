import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { PaymentType } from '../model/students.model';
import { StudentsService } from '../services/students.service';
import { SnackbarService } from '../services/snackbar.service';


@Component({
  selector: 'app-new-payment',
  templateUrl: './new-payment.component.html',
  styleUrl: './new-payment.component.css'
})
export class NewPaymentComponent implements OnInit {
  paymentFormGroup! : FormGroup;
  studentCode! : string;
  paymentTypes : string[]=[];
  pdfFileUrl! : string;
  showProgress : boolean = false;
  showPdf : boolean = true;
  
 

      constructor(private fb : FormBuilder,
         private activatedRoute : ActivatedRoute,
         private studentsService : StudentsService,
        private snackbarService : SnackbarService ){

      }

      ngOnInit(): void {
        for(let elt in PaymentType){
          let value = PaymentType[elt];
          if(typeof value === 'string'){
            this.paymentTypes.push(value);
          }
        }
        this.studentCode = this.activatedRoute.snapshot.params['studentCode']
          this.paymentFormGroup = this.fb.group({
             date : this.fb.control(''),
             amount : this.fb.control(''),
             type : this.fb.control(''),
             studentCode : this.fb.control(this.studentCode),
             fileSource : this.fb.control(''),
             fileName : this.fb.control(''),
          });
      }

      selectFile(event: any){
        this.showPdf = true;
        if(event.target.files.length>0){
          let file = event.target.files[0];
          this.paymentFormGroup.patchValue({
            fileSource : file,
            fileName : file.name,
          });
          this.pdfFileUrl = window.URL.createObjectURL(file);
        }
      }

      savePayment(){
        this.showProgress = true;
         let date: Date = new Date(this.paymentFormGroup.value.date);
         let formattedDate = date.getDate()+"/"+(date.getMonth()+1)+"/"+date.getFullYear();
         let formData = new FormData();
         formData.set('date', formattedDate);
         formData.set('amount', this.paymentFormGroup.value.amount);
         formData.set('type', this.paymentFormGroup.value.type);
         formData.set('studentCode', this.paymentFormGroup.value.studentCode);
         formData.set('file', this.paymentFormGroup.value.fileSource);
         this.studentsService.savePayment(formData).subscribe(
          {
             next : payment =>{
              this.showProgress = false;
              this.showPdf = false;
              this.snackbarService.show('Payement enregistre avec succes!','snackbar-success');
              this.paymentFormGroup.reset();  
             
              //alert('Payment Saved successfully!')
             },
             error : err =>{
              console.log(err);
              this.showProgress = false;
              console.log(err.status);
              if(err.status === 400){
                this.snackbarService.show('Ressource introuvale', 'snackbar-warning');
              }else if(err.status === 401){
                this.snackbarService.show('Vous n\'etes pas connectes', 'snackbar-warning');
              }else if(err.status === 403){
                this.snackbarService.show('Vous n\'avez pas les autorisations', 'snackbar-warning');
              }else{
                this.snackbarService.show('Failed to save payment', 'snackbar-error');
              }
             }
         }
        );
      }

      afterLoardComplete(event: any){
        console.log(event);
      }

}


