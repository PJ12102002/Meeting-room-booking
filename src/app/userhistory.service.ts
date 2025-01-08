import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserhistoryService {

   constructor(private http: HttpClient) {}
    //apiURL: string = "http://localhost:3000";
    getPastHistory(): Observable<any> {
      return this.http.get<any>(`http://localhost:8080/user/bookHistory`); }
       getCurrentHistory(): Observable<any> {
        return this.http.get<any>(`http://localhost:8080/user/currentHistory`);
      }
   
        // Method to cancel a booking
    cancelBooking(bookId: number): Observable<any> {
      // Assuming the backend supports a DELETE or PUT method to cancel a booking by ID
      return this.http.get<any>(`http://localhost:8080/user/cancelRoom/${bookId}`,{});
      // Adjust the URL and method (PUT/DELETE) based on your backend's requirements
    }
}
