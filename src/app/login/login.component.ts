
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html', 
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{

  public loginFormGroup! : FormGroup;

  public erroMessage! : any;

  constructor(private fb : FormBuilder,
     private authService : AuthenticationService,
     private router : Router){

  }

  ngOnInit(): void {
    this.loginFormGroup = this.fb.group({
      username : this.fb.control(''),
      password : this.fb.control(''),
    });
  
  }

  login() {
    let username = this.loginFormGroup.value.username;
    let password = this.loginFormGroup.value.password;
     /*let auth = this.authService.login(username,password);
     console.log(auth)
    if(auth==true){
      this.router.navigateByUrl("/admin")
    }
  */
     this.authService.login(username,password).subscribe({
      next : data => {
         this.authService.loadProfile(data);
         this.router.navigateByUrl("/admin")
      },
      error : err => {
      this.erroMessage = "Username ou mot de passe incorrect."
          console.log(err);
      }
    }) 
  }

}
