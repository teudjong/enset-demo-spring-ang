import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { StudentsService } from '../services/students.service';

@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrl: './payments.component.css'
})
export class PaymentsComponent implements OnInit{
  public payments! : any;
  public dataSource! : any;
  public displayedColumns = ['id','date','amount','type','status','firstName'];
  showProgress : boolean = false;
  @ViewChild(MatPaginator) paginator! : MatPaginator;
  @ViewChild(MatSort) sort! : MatSort;

  
  constructor(private studentsService : StudentsService){

  }

  ngOnInit(): void {
    this.showProgress = true;
      this.studentsService.getAllPayments()
      .subscribe({
        next : data => {
          this.showProgress = false;
          this.payments = data;
          this.dataSource = new MatTableDataSource(this.payments);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        },
        error : err => {
          this.showProgress = false;
          console.log(err);
        }

      })
  }

  filterPayments(event:Event) :void{
    let value =(event.target as HTMLInputElement).value;
    this.dataSource.filter = value;
 }
}
