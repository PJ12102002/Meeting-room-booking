import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AdminProfileService {
  private baseUrl = 'http://localhost:8080/admin';  // Replace with the actual backend URL

  constructor(private http: HttpClient) {}

  // Method to get the admin profile
  getAdminProfile(): Observable<any> {
    return this.http.get(`${this.baseUrl}/profile`);
  }

  // Method to update the admin profile
  updateAdminProfile(adminData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/profile`, adminData);
  }
}
