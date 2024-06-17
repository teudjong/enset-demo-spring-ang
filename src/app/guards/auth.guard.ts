import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot,
   GuardResult,
    MaybeAsync,
     Router,
     RouterStateSnapshot
    } from '@angular/router';

import { AuthenticationService } from '../services/authentication.service';



@Injectable()
export class AuthGuard  {

  constructor(private authService : AuthenticationService,
              private router : Router){

  }

 canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
  console.log(this.authService.authenticated)
  if(this.authService.authenticated ==true){
      console.log()
      return true;
    }else{
      this.router.navigateByUrl("/login")
      return false;
    }

  }
 }

