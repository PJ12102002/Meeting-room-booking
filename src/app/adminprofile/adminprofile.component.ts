import { Component } from '@angular/core';
import { AdminProfileService } from '../adminprofile.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-adminprofile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './adminprofile.component.html',
  styleUrl: './adminprofile.component.css'
})
export class AdminprofileComponent {

  adminData: any = {};  // Object to hold the admin data
  isEditing: boolean = false;  // To toggle between view/edit mode

  constructor(
    private adminProfileService: AdminProfileService,
    private router: Router
  ) {
    this.fetchAdminProfile(); // Fetch profile directly in the constructor
  }

  // Method to fetch admin profile data
  fetchAdminProfile(): void {
    this.adminProfileService.getAdminProfile().subscribe({
      next: (response) => {
        if (response && response.zid) {
          this.adminData = response;
        } else {
          console.error('No profile found');
        }
      },
      error: (err) => {
        console.error('Error fetching profile data', err);
      }
    });
  }

  // Toggle edit mode
  toggleEdit(): void {
    this.isEditing = !this.isEditing;
  }

  // Method to update the admin profile
  updateProfile(): void {
    this.adminProfileService.updateAdminProfile(this.adminData).subscribe({
      next: (response) => {
        alert('Profile updated successfully');
        this.isEditing = false;
      },
      error: (err) => {
        alert('Error updating profile');
        console.error('Error:', err);
      }
    });
  }
}
