import { Component, Input } from '@angular/core';
import { AdminmeetingService } from '../adminmeeting.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-adminbookroom',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './adminbookroom.component.html',
  styleUrl: './adminbookroom.component.css'
})
export class AdminbookroomComponent {

  requestedStartDate: string = '';
    requestedStartTime: string = '';
    requestedEndDate: string = '';
    requestedEndTime: string = '';
    availableRooms: any[] = [];
    selectedRoomNo: string = '';
    message: string = '';
    todayDate: string = '';
    currentTime: string = '';
    meetingRooms: any[] = [];
    // Pagination variables
    currentPage: number = 1;
    itemsPerPage: number = 1;  // Number of rooms per page
    totalRooms: number = 0;
   
    @Input() Rooms: any[] = [];
   
    constructor(private meetingRoomService: AdminmeetingService,private router :Router,private activatedRoute: ActivatedRoute) {
      // Set the default date to today's date
      const today = new Date();
      this.todayDate = today.toISOString().split('T')[0];  // Format: YYYY-MM-DD
   
      // Set the current time for start time input
      this.currentTime = today.toISOString().split('T')[1].substring(0, 5);  // Format: HH:mm
   
      const navigation = this.router.getCurrentNavigation();
      if (navigation?.extras.state) {
        this.meetingRooms = navigation.extras.state['meetingRooms'];
      }
    }
   
    // Method to check room availability
    checkAvailability() {
     
      this.meetingRoomService.checkRoomAvailability(
        this.meetingRooms,
        this.requestedStartDate,
        this.requestedStartTime,
        this.requestedEndDate,
        this.requestedEndTime
      ).subscribe((response) => {
        this.availableRooms = response;
        // this.totalRooms = response.length;  // Set the total number of rooms available
        // this.paginateRooms();
   
      });
     
    }
  // Pagination: Slice the available rooms array to show only the rooms for the current page
  // paginateRooms() {
  //   const startIndex = (this.currentPage - 1) * this.itemsPerPage;
  //   const endIndex = startIndex + this.itemsPerPage;
  //   this.availableRooms = this.availableRooms.slice(startIndex, endIndex);
  // }
   
  // Method to handle the next page
  // nextPage() {
  //   if ((this.currentPage * this.itemsPerPage) < this.totalRooms) {
  //     this.currentPage++;
  //     this.paginateRooms();
  //   }
  // }
   
  // Method to handle the previous page
  // previousPage() {
  //   if (this.currentPage > 1) {
  //     this.currentPage--;
  //     this.paginateRooms();
  //   }
  // }
   
   
    // Method to book a room
    bookRoom(roomNo:string) {
      this.selectedRoomNo=roomNo;
      if (!this.selectedRoomNo) {
        alert("Please select a room first.");
        return;
      }
   
      // Call the backend service to book the room
      this.meetingRoomService.bookRoom(roomNo).subscribe(
        (response:any) => {
          alert('Room booked successfully!');
          this.message = 'Room booked successfully!';
   
          this.router.navigate(['/dropdown']);
        },
        (error:any) => {
          console.error(error);
          alert('Error in booking the room!');
          this.message = 'Error in booking the room!';
        }
      );
    }
   
   
    onStartDateChange() {
      if (this.requestedEndDate && this.requestedEndDate < this.requestedStartDate) {
        this.requestedEndDate = '';
      }
    }
   
    onStartTimeChange() {
      if (this.requestedEndTime && this.requestedEndTime < this.requestedStartTime) {
        this.requestedEndTime = '';
      }
    }
   
    onEndDateChange() {
      if (this.requestedEndDate < this.requestedStartDate) {
        this.requestedEndDate = '';
      }
    }
   
    onEndTimeChange() {
      if (this.requestedEndTime < this.requestedStartTime) {
        this.requestedEndTime = '';
      }
    }

}
