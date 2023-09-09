import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const USER_API = 'http://localhost:8080/api/user/';

@Injectable({
  providedIn: 'root'
})


export class UserService {

  constructor(private http: HttpClient) { }

  // get user by id
  getUserById(id: number): Observable<any> {
    return this.http.get(USER_API + id);
  }

  // getting current user
  getCurrentUser(): Observable<any> {
    return this.http.get(USER_API);
  }


}
