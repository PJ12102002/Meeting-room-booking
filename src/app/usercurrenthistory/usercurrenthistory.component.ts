import { Component, inject } from '@angular/core';
import { UserhistoryService } from '../userhistory.service';
import { firstValueFrom } from 'rxjs';
import { NgxPaginationModule } from 'ngx-pagination';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-usercurrenthistory',
  standalone: true,
  imports: [NgxPaginationModule, CommonModule, ],
  templateUrl: './usercurrenthistory.component.html',
  styleUrl: './usercurrenthistory.component.css'
})
export class UsercurrenthistoryComponent {
  currentHistory: any[] = [];
  currentPage: number = 1;
  errorMessage: string = '';
  private httpcrudService = inject(UserhistoryService);
  constructor() { this.loadCurrentHistory(); }
  async loadCurrentHistory(): Promise<void> {
    try {
      const data = await firstValueFrom(this.httpcrudService.getCurrentHistory());
      this.currentHistory = data && data.length > 0 ? data : [];
      console.log(this.currentHistory);
    }
    catch (error) {
        this.errorMessage = 'Could not load current history data';
         console.error('Error loading current history data', error);
        }
        console.log(this.currentHistory);
 
      }
      async cancelBooking(bookId: number): Promise<void> {
        // const booking = this.currentHistory[index];
        if (confirm(`Are you sure you want to cancel the booking   `)) {
          try {
            await firstValueFrom(this.httpcrudService.cancelBooking(bookId));
            this.loadCurrentHistory();
            alert('Booking canceled successfully!');
           // this.loadBookingHistory(); // Reload the histories to ensure consistency
            } catch (error) {
              this.errorMessage = 'Could not cancel the booking';
              console.error('Error cancelling the booking', error);
            }
          }
        }
     

}
