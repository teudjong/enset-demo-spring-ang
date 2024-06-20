import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from './services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'frontend-angular';

   constructor(private authSevice : AuthenticationService){

   }

  ngOnInit(): void {
      this.authSevice.loadJwtTokenFromLocalStorage();
  }
}
