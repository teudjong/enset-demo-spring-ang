import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot,
   GuardResult,
    MaybeAsync,
     Router,
     RouterStateSnapshot
    } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';

@Injectable()
export class AuthorizationGuard  {

  constructor(private authService : AuthenticationService,
              private router : Router){

  }

 canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
   let authorize = false;
   let authorizedRoles : string[] = route.data['roles'];
   let roles : string[] = this.authService.roles as string[];
   console.log(roles)
   for (const element of roles){
    if(authorizedRoles.includes(element)){
      authorize = true;
    }
   }

   return authorize;
 }
}
