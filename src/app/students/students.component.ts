import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { StudentsService } from '../services/students.service';
import { Student } from '../model/students.model';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrl: './students.component.css'
})
export class StudentsComponent implements OnInit, AfterViewInit{

public students! : Array<Student>;
public dataSource! : MatTableDataSource<Student>;
public displayedColumns =["id","firstName","lastName","code","programId","photo","payments"];
@ViewChild(MatPaginator) paginator! : MatPaginator;
@ViewChild(MatSort) sort! : MatSort;
showProgress : boolean = false;


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

      this.showProgress = true;
      this.studentsService.getStudents()
      .subscribe({
        next : data => {
          this.showProgress = false;
          this.students = data;
          this.dataSource = new MatTableDataSource(this.students);
        },
        error : err => {
          this.showProgress = false;
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

    getPayments(student: any) {
      this.router.navigateByUrl(`/admin/student-details/${student.code}`);
    }
}

