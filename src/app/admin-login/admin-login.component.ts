import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminloginService } from '../adminlogin.service';

@Component({
  selector: 'app-admin-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-login.component.html',
  styleUrl: './admin-login.component.css'
})
export class AdminLoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private router: Router, private authService: AdminloginService) {}

  login() {
    this.errorMessage = '';

    // Validate user input
    if (!this.username || !this.password) {
      this.errorMessage = 'Both username and password are required.';
      return;
    }

    if (this.username.length < 3) {
      this.errorMessage = 'Username must be at least 3 characters long.';
      return;
    }

    if (this.password.length < 6) {
      this.errorMessage = 'Password must be at least 6 characters long.';
      return;
    }

    // Call the login method of the AuthService
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        // Handle successful login response
        if (response && response.zid) {
          console.log('Login successful:', response);
          localStorage.setItem('fullName', response.fullName);
          localStorage.setItem('zid', response.zid);
          this.router.navigate(['/admin-dashboard']);
        } else {
          this.errorMessage = 'Username or password is incorrect.';
        }
      },
      error: (err) => {
        // Handle error response (e.g., server issues)
        this.errorMessage = 'An error occurred. Please try again later.';
      },
    });
  }

}
