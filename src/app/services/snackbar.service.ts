import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {

  constructor(private snackBar: MatSnackBar) { }

  show(message: any, className: any){
    this.snackBar.open(message, '', {
      duration: 3000,
      panelClass: [className],
      horizontalPosition: 'right',
      verticalPosition: 'top'
    });
  }
}
