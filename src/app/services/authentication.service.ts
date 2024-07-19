import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  public exp!: any;
  public username! : any;
  public roles! : string[];
  public authenticated = false;
  public authorized = false;
  public accessToken!: any;
  public users:any = {
  'admin': ['STUDENT','ADMIN'],
  'user1': ['STUDENT']
  }

  constructor(private router: Router, private http: HttpClient) {

  }

  public login(username:string , password: string) {
    let options = {
      headers : new HttpHeaders().set("Content-Type","application/x-www-form-urlencoded")
    }
    let params=new HttpParams()
    .set("username",username).set("password",password);
    return this.http.post("http://localhost:8021/auth/login", params, options)
     /* if(this.users[username] && password=="1234"){
      this.username = username;
      this.roles = this.users[username];
      this.authenticated = true;
      return true;
    }else{
      return false; 
    }  */
  } ; 

  loadProfile(data : any){
     this.authenticated=true;
     this.accessToken = data['access_token'];
     let decodeJwt:any = jwtDecode(this.accessToken);
     this.username = decodeJwt.sub;
     this.exp = decodeJwt.exp;
     this.roles = decodeJwt.scope.split(' ');
     window.localStorage.setItem("jwt-token",this.accessToken);
  }

  logout(){
    this.authenticated = false;
    this.username = undefined;
    this.accessToken= undefined;
    this.roles = [];
    //window.localStorage.clear();
    //window.localStorage.removeItem("jwt-token");
    this.router.navigateByUrl("/login");
  }

  loadJwtTokenFromLocalStorage(){
    let token = window.localStorage.getItem("jwt-token");
    if(token){
      this.loadProfile({"access_token" : token});
      //console.log(token)
      this.router.navigateByUrl("/admin/students");
    }
  }
}
