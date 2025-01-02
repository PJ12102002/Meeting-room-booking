import { Component } from '@angular/core';
import { Router } from '@angular/router';  // Router for navigation
import { CommonModule } from '@angular/common';  // CommonModule for directives like ngIf, ngFor
import { FormsModule } from '@angular/forms';  // FormsModule for ngModel
import { SignupService } from '../signup.service';
import { LoginserviceService } from '../loginservice.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  standalone: true,  // Mark as standalone component
  imports: [CommonModule, FormsModule],  // Import necessary modules
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})


export class LoginComponent {
  username: string = '';  // Capturing username
  password: string = '';  // Capturing password
  errorMessage: string = '';  // Variable to store error messages

  constructor(private router: Router, private loginService: LoginserviceService, private userService: UserService) {}

  // Method to handle login form submission
  login() {
    // Reset error message on each login attempt
    this.errorMessage = '';

    // Validate username and password (optional validation since we have HTML5 validations)
    if (!this.username || !this.password) {
      this.errorMessage = 'Both username and password are required.';
      return;
    }

    // Call the login service to authenticate the user
    this.loginService.login(this.username, this.password).subscribe(
      (response) => {
        // If the response is an error message
        if (response === 'Username or password is incorrect.') {
          this.errorMessage = response; // Display error message from backend
        } else {
          // If login is successful, store the user info (optional) and navigate to home
          console.log('Login successful');
          console.log('User Data:', response); // Log the user data returned from backend
          // const name = this.userService.setFullName(response.fullName);
          // console.log(name);
         
            this.userService.setFullName(response.fullName);
            console.log('Full Name Set:', response.fullName);
      
          
          // Optionally store the user data (e.g., user ID, token) in localStorage
          localStorage.setItem('user', JSON.stringify(response));
          

          // Navigate to home page or dashboard
          this.router.navigate(['/emp-dashboard']);
        }
      },
      (error) => {
        // Handle any error during the login process
        console.error('Login failed', error);
        this.errorMessage = 'An error occurred. Please try again later.';
      }
    );
  }

  // Navigate to signup page
  goToSignup() {
    this.router.navigate(['/signup']);
  }
}
