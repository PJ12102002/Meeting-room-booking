import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})

export class UserService {
  
  private fullName: string = '';


  constructor(@Inject(PLATFORM_ID) private platformId: Object) {
    // Check if we're in the browser environment
    if (isPlatformBrowser(this.platformId)) {
      const storedFullName = localStorage.getItem('fullName');
      if (storedFullName) {
        this.fullName = storedFullName;
      }
    }
  }

  setFullName(name: string) {
    this.fullName = name;
    localStorage.setItem('fullName', name); // Save to localStorage
  }

  getFullName(): string {
    return this.fullName;
  }
}
