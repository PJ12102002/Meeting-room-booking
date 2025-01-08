import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders,HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
 
@Injectable({
  providedIn: 'root'
})
export class MeetingRoomService {
  private baseUrl = 'http://localhost:8080/user'; // Change this to your backend URL
 
  constructor(private http: HttpClient) { }
 
  // Request for room availability
  checkRoomAvailability(meetingRoom :any[],startDate: string, startTime: string, endDate: string, endTime: string): Observable<any> {
   
    const url = `${this.baseUrl}/availability`;
    // Construct the query parameters using HttpParams
    const params = new HttpParams()
      .set('requestedStartDate', startDate)
      .set('requestedStartTime', startTime)
      .set('requestedEndDate', endDate)
      .set('requestedEndTime', endTime);
 
    // Send POST request with the meetingRoom data in the body and query params
    return this.http.post<any>(url, meetingRoom, { params });
  }
  // Booking a room
  bookRoom(roomNo: string): Observable<any> {
    const url = `${this.baseUrl}/booking`;
    const params = new HttpParams()
      .set('roomNo', roomNo.toString());
   
    return this.http.post<any>(url, null,{params});
  }
}
 
 