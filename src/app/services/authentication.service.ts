import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
 public username! : any;
  public roles : any;
  public authenticated = false;
  public accessToken!: string;
  public users:any = {
  'admin': ['STUDENT','ADMIN'],
  'user1': ['STUDENT']
  }

  constructor(private router: Router, private http: HttpClient) {

  }

  public login(username:string , password: string) {
    let options = {
      headers : new HttpHeaders().set("Content-Type","application/x-www-form-urlencoded")
      .set("Content-Type","application/x-www-form-urlencoded")
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
     this.roles = decodeJwt.scope;
  }



  logout(){
    this.authenticated = false;
    this.username = undefined;
    this.roles = undefined;
    this.router.navigateByUrl("/login");
  }
}
