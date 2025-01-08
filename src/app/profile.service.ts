import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
 
export interface Employee {
  zid: string;
  designation: string;
  email: string;
  fullName: string;
  part: string;
  password: string;
  path: string;
  role: string;
}
 
@Injectable({
  providedIn: 'root'
})
export class ProfileService {
 
  private baseUrl = 'http://localhost:8080';  // Backend API endpoint for getting profile
  constructor(private http: HttpClient) {}
 
  getProfile(): Observable<Employee> {
    return this.http.get<Employee>(`${this.baseUrl}/user/profile`);
  }
 
  updateProfile(employee: Employee): Observable<any> {
    return this.http.post(`${this.baseUrl}/user/profile`, employee);
  }
 
 
}
 
 