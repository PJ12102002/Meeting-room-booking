import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  private apiUrl = 'http://localhost:8080/user/signUp';  
  

  constructor(private http:HttpClient) { }
  signup(userData: { username: string, password: string }): Observable<any> {
    // Create URL parameters
    const params = new HttpParams().set('username', userData.username).set('password', userData.password);

    // Send POST request with URL parameters
    return this.http.post(this.apiUrl, null, { params });
  }





  

 

}
