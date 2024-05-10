import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
 public username! : any;
  public roles : any;
  public authenticate : boolean = false;


  constructor() { }

  public login(username:string , password: string) {
    if(username=="admin" && password=="1234"){
      this.username = username;
      this.roles = ("ADMIN");
      this.authenticate = true;
      return true;
    }else{
      return false;
    }
  } ;
  
  logout(){
    this.authenticate = false;
    this.username = undefined;
    this.roles = undefined;
  }
}
