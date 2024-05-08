import { AfterContentChecked, AfterViewChecked, AfterViewInit, Component, OnInit, ViewChild, viewChild } from '@angular/core';
import { MatListSubheaderCssMatStyler } from '@angular/material/list';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

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

constructor(){

}
  ngOnInit(): void {
      this.students=[];
      for (let i:number = 1; i<100; i++){
        this.students.push(
          {
            id : i,
            firstName : Math.random().toString(20),
            lastName : Math.random().toString(20),
          }
        );
      }
      this.dataSource = new MatTableDataSource(this.students)
  }

  ngAfterViewInit(): void {
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;

  }
}
