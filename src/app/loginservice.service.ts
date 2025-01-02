import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginserviceService {

  private apiUrl = 'http://localhost:8080/user/login';  // Update this URL to match your backend

  constructor(private http: HttpClient) {}

  // Login method to send credentials to the backend
  login(username: string, password: string): Observable<any> {
    const params = new HttpParams()
      .set('zid', username)       // Send the username (zid) as a parameter
      .set('password', password); // Send the password as a parameter

    return this.http.post(this.apiUrl, null, { params });
  }
}
