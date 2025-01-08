import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  images = [
    './images/meeting-room1.png',
    './images/meeting-room2.png',
    './images/image.png'
  ];

  currentIndex = 0;
  showLoginDialog = false;
  loginType = '';
  showShareDialog = false;

  constructor(private router: Router) {}

  scrollRight(): void { 
    if (this.currentIndex < this.images.length - 1) { 
      this.currentIndex++; 
      this.updateScrollPosition(); 
    } 
  }

  scrollLeft(): void {
    if (this.currentIndex > 0) {
      this.currentIndex--; 
      this.updateScrollPosition(); 
    } 
  }

  updateScrollPosition(): void {
    const slider = document.querySelector('.slider') as HTMLElement; 
    const imageWidth = slider.offsetWidth;  // Dynamically adjust based on container width
    slider.style.transform = `translateX(-${this.currentIndex * imageWidth}px)`; 
  }

  openLoginDialog() {
    this.showLoginDialog = true;
  }

  closeLoginDialog() {
    this.showLoginDialog = false;
  }

  loginAsEmployee() {
    this.loginType = 'employee';
    this.router.navigate(['/login']);
    this.closeLoginDialog();
  }

  loginAsAdmin() {
    this.loginType = 'admin';
    this.router.navigate(['/admin-login']);
    this.closeLoginDialog();
  }

  openShareDialog() {
    this.showShareDialog = true;
  }

  closeShareDialog() {
    this.showShareDialog = false;
  }
}
