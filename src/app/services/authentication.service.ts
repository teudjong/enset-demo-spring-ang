import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor() { }

  public login(username:string , password: string) {
    if(username=="admin" && password=="1234"){
      return true;
    }else{
      return false;
    }
  } ;
  
}
