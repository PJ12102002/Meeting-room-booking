import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';  // Import HttpClientModule
import { SignupService } from '../signup.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],  // Add HttpClientModule here
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  login(username: string, password: string) {
    throw new Error('Method not implemented.');
  }
  username: string = '';
  password: string = '';
  confirmPassword: string = '';
  errorMessage: string = '';

  constructor(private router: Router, private service: SignupService) {}

  signup() {
    this.errorMessage = '';

    // Validate if the passwords match
    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'Passwords do not match.';
      return;
    }

    // Call the signup service with the username and password in an object
    const userData = { username: this.username, password: this.password };
    this.service.signup(userData).subscribe(
      (response) => {
        console.log('User signed up successfully', response);
        this.username = '';
        this.password = '';
        this.confirmPassword = '';
        this.router.navigate(['/']);
      },
      (error) => {
        console.error('Error signing up', error);
        this.errorMessage = 'There was an error signing up. Please try again.';
      }
    );
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }
}
