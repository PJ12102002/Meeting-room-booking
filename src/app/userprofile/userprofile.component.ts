import { Component } from '@angular/core';
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
export class UserprofileComponent {

  employee: Employee | null = null;  // Store the user profile
  errorMessage: string = '';  // Error message if any
  isEditing: boolean = false;  // Flag to toggle between view and edit mode
  selectedFile: File | null = null; // Store the selected file

  constructor(private profileService: ProfileService, private router: Router) {}

  // Method to load the profile data when the user requests it
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

  // Handle file selection
  onFileSelected(event: any) { 
    const file: File = event.target.files[0]; 
    const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png']; 
    if (file && allowedTypes.includes(file.type)) { 
      this.selectedFile = file; 
      this.errorMessage = ''; 
    } 
    else {
       this.selectedFile = null; 
       this.errorMessage = 'Invalid file type. Only JPEG, JPG, and PNG files are allowed.'; 
       event.target.value = ''; // Clear the input to reset file selection
      } 
    }

  // Save the updated profile data
  saveProfile() {
    if (this.employee) {
        this.profileService.uploadFile(this.selectedFile).subscribe(
            (response) => {
                if (this.employee) {
                    this.employee.path = response.filePath;  // Set the file path from the response
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
            },
            (error) => {
                console.error('Error uploading file:', error);
                this.errorMessage = error.error ? error.error : 'An error occurred while uploading the file.';
            }
        );
    }
}

  // Navigate to another page (if needed)
  goBack() {
    this.router.navigate(['/emp-dashboard']);
  }
 
}
