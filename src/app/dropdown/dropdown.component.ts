import { Component } from '@angular/core';
import { PhaseService } from '../phase.service';
import { RoomService } from '../room.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { BookroomComponent } from '../bookroom/bookroom.component';
import { BookingService } from '../booking.service';

@Component({
  selector: 'app-dropdown',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule, BookroomComponent],
  templateUrl: './dropdown.component.html',
  styleUrl: './dropdown.component.css'
})
export class DropdownComponent {

  phases: any[] = [];
  floors: any[] = [];
 
 
  // Selected phase and floor
  selectedPhase: number = 1;
  selectedFloor: number = 1;
 
  // Available meeting rooms
  meetingRooms: any[] = [];
  message: string = '';
  flag:number=1;
 
  // @Output() booking:EventEmitter<any> =new EventEmitter<any>();
 
  constructor(private phaseService: PhaseService, private bookingService: BookingService,private router: Router) {
    this.phaseService.getPhases().subscribe((data) => {
      this.phases = data.phases;
    });
  }
 
  // ngOnInit(): void {
   
  // }
 
 
  onPhaseChange(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const phaseId = Number(selectElement.value);
    this.selectedPhase = phaseId;
 
    // Find selected phase and update floors accordingly
    const selectedPhase = this.phases.find((phase) => phase.id === phaseId);
    this.floors = selectedPhase ? selectedPhase.floors : [];
   
    // Reset the selected floor when phase changes
    this.selectedFloor = 1;
  }
 
  // Handle Floor Change
  onFloorChange(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    this.selectedFloor = Number(selectElement.value);
  }
 
  // Fetch available meeting rooms from the backend
  fetchAvailableRooms(): void {
    this.bookingService.fetchMeetingRooms(this.selectedPhase, this.selectedFloor)
      .subscribe(
        (response: any) => {
          if (response && response.message && response.message === "No Access") {
            console.log(response);
            this.message = 'No Access to meeting rooms for the selected phase and floor.';
            this.meetingRooms = [];
          } else {
            console.log(response);
            this.meetingRooms = response;
            this.message = '';
            // Navigate to booking component after fetching rooms
            this.router.navigate(['/bookingg'],{ state: { meetingRooms: this.meetingRooms } });
          }
        },
        (error) => {
          console.error(error);
          this.message = 'Error fetching meeting rooms';
          this.meetingRooms = [];
        }
      );
    
  }

}
