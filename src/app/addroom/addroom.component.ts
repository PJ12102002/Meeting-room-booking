import { Component } from '@angular/core';
import { RoomService } from '../room.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-addroom',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './addroom.component.html',
  styleUrl: './addroom.component.css'
})
export class AddroomComponent  {
  room: any = {
    roomNo: '',
    roomName: '',
    roomCapacity: 0,
    access: '',
    phaseNo: 1,
    floorNo: 1
  };
  
  message: string = '';

  constructor(private roomService: RoomService) {}

  // Method to add a room
  addRoom(): void {
    this.roomService.addRoom(this.room).subscribe(
      (response: any) => {
        if (response.message === 'Yes') {
          this.message = 'Room added successfully.';
          console.log("room added"); 
        } else {
          this.message = 'Error: ' + response.message;
        }
      },
      (error) => {
        this.message = 'Error adding room';
      }
    );
  }

}
