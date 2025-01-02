import { Injectable } from '@angular/core';
import { HttpClient,HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ComplaintService {
  private apiUrl = 'http://localhost:8080/admin/complaint';  // URL for the mock server API endpoint
 
  constructor(private http: HttpClient) {}
  getComplaint(){
    return  this.http.get<any>(this.apiUrl);
  }
  postResolve(id : number): Observable <any> {
     const params = new HttpParams()
          .set('compId',id.toString());
    return this.http.post<any>(this.apiUrl,{},{params});
  }
}
