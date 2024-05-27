import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { StudentsService } from '../services/students.service';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrl: './students.component.css'
})
export class StudentsComponent implements OnInit, AfterViewInit{

public students : any;
public dataSource : any;
public displayedColumns =["id","firstName","lastName","payments"]
@ViewChild(MatPaginator) paginator! : MatPaginator;
@ViewChild(MatSort) sort! : MatSort;

constructor(private router : Router,private studentsService: StudentsService){

}
  ngOnInit(): void {
      this.students=[];
      /* for (let i:number = 1; i<100; i++){
        this.students.push(
          {
            id : i,
            firstName : Math.random().toString(20),
            lastName : Math.random().toString(20),
          }
        );
      }
      this.dataSource = new MatTableDataSource(this.students) */

      this.studentsService.getStudents()
      .subscribe({
        next : data => {
          this.students = data;
          this.dataSource = new MatTableDataSource(this.students);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        },
        error : err => {
          console.log(err);
        }

      })
  }

  ngAfterViewInit(): void {
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;

  }
    filterStudents(event:Event) :void{
       let value =(event.target as HTMLInputElement).value;
       this.dataSource.filter = value;
    }

    getPayments(student :  any) :void {
      this.router.navigateByUrl("/payments")
    }
}

