import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class PhaseService {

  private apiUrl = 'assets/data.json';  // Path to the JSON file
 
  constructor(private http: HttpClient) {}
 
  getPhases(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }
}
