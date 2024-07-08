import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-template',
  templateUrl: './admin-template.component.html',
  styleUrl: './admin-template.component.css'
})
export class AdminTemplateComponent implements OnInit {
  


  constructor(public authService : AuthenticationService,
    private router : Router
  ){

  }
  ngOnInit(): void {
      
  }
  handleLogout() {
    this.authService.logout();
    
  }

  loadPayment(){
    if(this.authService.roles.includes('ADMIN')){
      this.router.navigateByUrl("/admin/payments");
    }else{
      this.router.navigateByUrl("/admin/notAuthorized");
    }

  }

  loadStudent(){
    if(this.authService.roles.includes('ADMIN')){
      this.router.navigateByUrl("/admin/students");
    }else{
      this.router.navigateByUrl("/admin/notAuthorized");
    }
  }
}
