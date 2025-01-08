import { Component, OnInit } from '@angular/core';
import { Employee, ProfileService } from '../profile.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-userprofile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './userprofile.component.html',
  styleUrl: './userprofile.component.css'
})
export class UserprofileComponent implements OnInit {

  employee: Employee | null = null;  // Store the user profile
  errorMessage: string = '';  // Error message if any
  isEditing: boolean = false;  // Flag to toggle between view and edit mode

  constructor(private profileService: ProfileService, private router: Router) {}

  ngOnInit(): void {
    // Automatically load the profile when the component is initialized
    this.loadProfile();
  }

  // Method to load the profile data when the component is initialized
  loadProfile(): void {
    this.profileService.getProfile().subscribe(
      (response: Employee) => {
        this.employee = response;
      },
      (error) => {
        console.error('Error fetching profile:', error);
        this.errorMessage = 'An error occurred while fetching the profile.';
      }
    );
  }

  // Toggle between view and edit mode
  toggleEdit() {
    this.isEditing = !this.isEditing;
  }

  // Save the updated profile data
  saveProfile() {
    if (this.employee) {
      this.profileService.updateProfile(this.employee).subscribe(
        (response) => {
          console.log('Profile updated successfully:', response);
          this.isEditing = false;  // Switch to view mode after saving
        },
        (error) => {
          console.error('Error updating profile:', error);
          this.errorMessage = error.error ? error.error : 'An error occurred while updating the profile.';
        }
      );
    }
  }
}
