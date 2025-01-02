import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  images = [
    './images/meeting-room1.png',
    './images/meeting-room2.png',
    './images/image.png'
  ];

  currentIndex = 0;

  

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

  showLoginDialog: boolean = false;
  loginType: string = ''; // To determine whether it's an employee or admin login

  constructor(private router: Router) {}
  

  // Show the login dialog
  openLoginDialog() {
    this.showLoginDialog = true;
  }

  // Close the login dialog
  closeLoginDialog() {
    this.showLoginDialog = false;
  }

  // Navigate to Employee Login
  loginAsEmployee() {
    this.loginType = 'employee';
    this.router.navigate(['/login']);
    this.closeLoginDialog();
  }

  // Navigate to Admin Login
  loginAsAdmin() {
    this.loginType = 'admin';
    this.router.navigate(['/admin-login']);
    this.closeLoginDialog();
  }

}
