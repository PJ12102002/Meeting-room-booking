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

  private apiUrl = 'http://localhost:8080/user/profile';  // Backend API endpoint for getting profile

  constructor(private http: HttpClient) { }

  // Method to fetch the profile data
  getProfile(): Observable<Employee> {
    return this.http.get<Employee>(this.apiUrl);
  }

  // Method to update the profile data
  updateProfile(employee: Employee): Observable<any> {
    // POST request to update the profile with the new data
    return this.http.post<any>(this.apiUrl, employee);
  }
}
