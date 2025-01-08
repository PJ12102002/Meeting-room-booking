import { Component } from '@angular/core';
import { PasthistoryService } from '../pasthistory.service';
import { firstValueFrom } from 'rxjs';
import { NgxPaginationModule } from 'ngx-pagination';
import { CommonModule } from '@angular/common';



@Component({
  selector: 'app-pasthistory',
  standalone: true,
  imports: [NgxPaginationModule, CommonModule],
  templateUrl: './pasthistory.component.html',
  styleUrl: './pasthistory.component.css'
})
export class PasthistoryComponent {
  pastHistory: any[] = [];
  currentPage: number = 1;
  errorMessage: string = '';
  constructor(private httpcrudService:PasthistoryService) {
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
 


