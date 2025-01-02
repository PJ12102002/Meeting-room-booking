import { Component } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { ComplaintService } from '../complaint.service';

@Component({
  selector: 'app-complaint',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './complaint.component.html',
  styleUrl: './complaint.component.css'
})
export class ComplaintComponent {

  complaints: any[] = [];
  message :String='';
  flag:number=0;
  currentPage = 1;
  pageSize = 5; // Set how many rows you want per page
  totalItems = this.complaints.length;

    constructor(private complaint: ComplaintService){
      this.complaint.getComplaint().subscribe((response: any) => {
        if (response && response.message === 'No') {
          this.message = 'No Complaints arise';
          this.complaints = [];
          this.totalItems = 0;

        } else {
          this.complaints = response;
          this.totalItems = response.length;
          this.message = '';
        }
      },
      (error) => {
        this.message = 'Error fetching meeting rooms';
        this.complaints = [];
        this.totalItems = 0;
      });
    }
    get pagedComplaints() {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      return this.complaints.slice(startIndex, startIndex + this.pageSize);
    }
  
    setPage(page: number) {
      if (page > 0 && page <= this.totalPages) {
        this.currentPage = page;
      }
    }  
    get totalPages() {
      return Math.ceil(this.totalItems / this.pageSize);
    }
resolveIssue(compId :number){
  console.log("hello");
  this.complaint.postResolve(compId).subscribe((response: any) => {
    if (response && response.message === 'No') {
      this.message = 'No Complaints arise';
      this.complaints = [];
      this.totalItems = 0;
      console.log("no");
    } else {
      this.complaints = response;
      this.message = '';
      this.totalItems = response.length;
      console.log("yes");
    }
  },
  (error) => {
    this.message = 'Error fetching meeting rooms';
    this.complaints = [];
    this.totalItems = 0;
  });

}

}
