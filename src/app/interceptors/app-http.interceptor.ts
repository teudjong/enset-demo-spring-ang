import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';

@Injectable()
export class AppHttpInterceptor implements HttpInterceptor{

  constructor(private authService : AuthenticationService){}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
     console.log("******")
     console.log(request.url);
     if(!request.url.includes("/auth/login")){
      let newRequest = request.clone({
        headers : request.headers.set('Authorization','Bearer'+this.authService.accessToken)
       })
       return next.handle(newRequest);
     }else return next.handle(request);
     
  }
}
