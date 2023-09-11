import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";


const AUTH_API = 'http://localhost:8080/api/auth/';

@Injectable({
  providedIn: 'root'
})

// service send request to auth controller
export class AuthService {

  constructor(private http: HttpClient) { }


  //send login controller

  public login(user): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      username: user.username,
      password: user.password
    });
  }


  //send register controller
  public register(user): Observable<any> {
    return this.http.post(AUTH_API + 'signup', {
      email: user.email,
      username: user.username,
      password: user.password
    });
  }
}
