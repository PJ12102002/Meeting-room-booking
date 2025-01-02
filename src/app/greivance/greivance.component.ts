import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { FeedbackService } from '../feedback.service';
import { PhaseService } from '../phase.service';
import { BookingService } from '../booking.service';

@Component({
  selector: 'app-greivance',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './greivance.component.html',
  styleUrl: './greivance.component.css'
})
export class GreivanceComponent {
  phases: any[] = [];
  floors: any[] = [];
  selectedPhase: number = 1;
  selectedFloor: number = 1;
  meetingRooms: any[] = [];
  message: string = '';
  room:string='';
  feedback:string='';
  mes:string='';
  flag:number=1;
 
  @Output() roomsUpdated: EventEmitter<any[]> = new EventEmitter<any[]>();
 
  constructor(private phaseService: PhaseService, private bookingService: BookingService,private feedbackservice :FeedbackService) {}
 
  ngOnInit(): void {
    this.phaseService.getPhases().subscribe((data) => {
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
    this.bookingService.fetchMeetingRooms(this.selectedPhase, this.selectedFloor).subscribe(
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
    this.flag=0;
}
feedbacksubmit(): void{
  if (!this.room || !this.feedback) {
    this.mes = 'Please select a room and provide feedback.';
    return;
  }
   console.log("hello");

  // Call the feedback service with selected data
  this.feedbackservice.feedback(this.selectedPhase, this.selectedFloor, this.room, this.feedback).subscribe(
    (response: any) => {
      // Handle response
      if (response && response.message == 'Register') {
        this.mes = 'Feedback submitted successfully!';
        alert("feedback submitted successfully");
      } else {
        this.mes = 'Error submitting feedback.';
        alert("Error submitting feedback, Try again later");
      }
    },
    (error) => {
      // Handle error
      this.mes = 'Error submitting feedback. Please try again later.';
      alert("Error submitting feedback, Try again later");
    }
  );
  this.flag=1;
}

}
