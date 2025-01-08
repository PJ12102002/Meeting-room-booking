import { Component } from '@angular/core';
import { UserhistoryService } from '../userhistory.service';
import { firstValueFrom } from 'rxjs';
import { NgxPaginationModule } from 'ngx-pagination';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-userpasthistory',
  standalone: true,
  imports: [NgxPaginationModule, CommonModule],
  templateUrl: './userpasthistory.component.html',
  styleUrl: './userpasthistory.component.css'
})
export class UserpasthistoryComponent 
{

   pastHistory: any[] = [];
    currentPage: number = 1;
    errorMessage: string = '';
    constructor(private httpcrudService:UserhistoryService) {
      this.loadPastHistory();
    }
   
    async loadPastHistory(): Promise<void> {
      try {
        const data = await firstValueFrom(this.httpcrudService.getPastHistory());
        this.pastHistory = data && data.length > 0 ? data : [];
        // console.log(this.pastHistory)
        console.log(this.pastHistory);
      }
        catch (error) { this.errorMessage = 'Could not load past history data';
          console.error('Error loading past history data', error);
        }
      }

}
