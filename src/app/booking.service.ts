import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class BookingService {

  private apiUrl = 'http://localhost:8080/user/location';  // URL for the mock server API endpoint
 
  constructor(private http: HttpClient) {}
 
  // Fetch meeting rooms based on phase and floor
  fetchMeetingRooms(phase: number, floor: number): Observable<any> {
    const params = new HttpParams()
      .set('phase', phase.toString())
      .set('floor', floor.toString());
 
    return this.http.get<any>(this.apiUrl, { params });
  }
  
}
