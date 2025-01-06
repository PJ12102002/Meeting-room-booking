import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminloginService {

  private apiUrl = 'http://localhost:8080/admin/login'; // Correct URL endpoint for login

  constructor(private http: HttpClient) {}

  // Method to verify zid and password
  login(zid: string, password: string): Observable<any> {
    const params = new HttpParams()
      .set('zid', zid)
      .set('password', password);

    return this.http.post(this.apiUrl, params);
  }
}
