import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

    private apiUrl = 'http://localhost:8080/user/complaint';  // URL for the mock server API endpoint
   
    constructor(private http: HttpClient) {}
   
    feedback(phase: number, floor: number, roomNo: string, feedback: string): Observable<any> { 
        const body = {     
          phaseNo: phase,    
           floorNo: floor,     
           roomNo: roomNo,     
           description: feedback   
          };  
         return this.http.post<any>(this.apiUrl, body);
         }
}