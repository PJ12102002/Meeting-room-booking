import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
 
@Injectable({
  providedIn: 'root'  // Makes the service available app-wide
})
export class RoomService {
 
  private apiUrl = 'http://localhost:8080/admin/location';  // URL for the mock server API endpoint
 
  constructor(private http: HttpClient) {}
 
  // Fetch meeting rooms based on phase and floor
  fetchMeetingRooms(phase: number, floor: number): Observable<any> {
    const params = new HttpParams()
      .set('phase', phase.toString())
      .set('floor', floor.toString());
 
    return this.http.get<any>(this.apiUrl, { params });
  }
  private apiUrls = 'assets/data.json';  // Path to the JSON file
 
  getPhases(): Observable<any> {
    return this.http.get<any>(this.apiUrls);
  }
 
  private removeRoomUrl='http://localhost:8080/admin/removeRoom';
 
  // Method to remove a room
  removeRoom(roomNo: String): Observable<any>{
    const params=new HttpParams().set('roomNo',roomNo.toString());
    return this.http.post<any>(this.removeRoomUrl, null, { params });
  }
 
  private addRoomUrl = 'http://localhost:8080/admin/addRoom'; // URL for addRoom endpoint
 
  // Method to add a room
  addRoom(room: any): Observable<any> {
    return this.http.post<any>(this.addRoomUrl, room);
  }
 
}