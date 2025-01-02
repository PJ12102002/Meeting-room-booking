import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImageService {
  private apiUrl = 'http://localhost:3000/images';

  constructor(private http: HttpClient) {}

  // Upload the image to the mock server
  uploadImage(imageData: any): Observable<any> {
    return this.http.post(this.apiUrl, imageData);
  }

  // Get stored images from the mock server
  getImages(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
