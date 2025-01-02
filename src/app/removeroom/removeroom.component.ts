import { Component, Output, EventEmitter } from '@angular/core';
import { RoomService } from '../room.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
 
interface Phase {
  id: number;
  name: string;
  floors: Floor[];
}
interface Floor {
  id: number;
  name: string;
}
 
@Component({
  selector: 'app-removeroom',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],  // Import required modules for HTTP and forms
  providers: [RoomService],
  templateUrl: './removeroom.component.html',
  styleUrls: ['./removeroom.component.css']
})
export class RemoveroomComponent {
 
  phases: any[] = [];
  floors: any[] = [];
  selectedPhase: number = 1;
  selectedFloor: number = 1;
  meetingRooms: any[] = [];
  message: string = '';
  room: string = '';
  mes: string = '';
 
  @Output() roomsUpdated: EventEmitter<any[]> = new EventEmitter<any[]>();
 
  constructor(private roomService: RoomService) {
    // Initialization logic directly in the constructor
    this.initializePhases();
  }
 
  // Method to initialize phases
  initializePhases(): void {
    this.roomService.getPhases().subscribe((data) => {
      this.phases = data.phases;
    });
  }
 
  // Handles phase selection change
  onPhaseChange(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const phaseId = Number(selectElement.value);
    this.selectedPhase = phaseId;
    const selectedPhase = this.phases.find((phase) => phase.id === phaseId);
    this.floors = selectedPhase ? selectedPhase.floors : [];
    this.selectedFloor = 1;
  }
 
  // Handles floor selection change
  onFloorChange(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    this.selectedFloor = Number(selectElement.value);
  }
 
  // Fetch available rooms based on phase and floor
  fetchAvailableRooms(): void {
    this.roomService.fetchMeetingRooms(this.selectedPhase, this.selectedFloor).subscribe(
      (response: any) => {
        if (response && response.message === 'No Access') {
          this.message = 'No Access to meeting rooms for the selected phase and floor.';
          this.meetingRooms = [];
        } else {
          this.meetingRooms = response;
          this.message = '';
        }
        // Emit available rooms to parent
        this.roomsUpdated.emit(this.meetingRooms);
      },
      (error) => {
        this.message = 'Error fetching meeting rooms';
        this.meetingRooms = [];
      }
    );
  }
 
  // Remove selected room
  removeRoom(): void {
    if (this.room) {
      this.roomService.removeRoom(this.room).subscribe(
        (response: any) => {
          if (response.message === 'Yes') {
            this.mes = 'Room removed successfully.';
            // Optionally, you can update the meeting rooms list after removal
            this.fetchAvailableRooms();
          } else {
            this.mes = 'Error: ' + response.message;
          }
        },
        (error) => {
          this.mes = 'Error removing room';
        }
      );
    } else {
      this.mes = 'Please select a room to remove.';
    }
  }
}
 
 