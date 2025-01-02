import { Component, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { Router, RouterOutlet} from '@angular/router';
import { MatDividerModule } from '@angular/material/divider';
import { AddroomComponent } from '../addroom/addroom.component';
import { RemoveroomComponent } from '../removeroom/removeroom.component';
import { ComplaintComponent } from '../complaint/complaint.component';

import { CommonModule } from '@angular/common';
import { UserprofileComponent } from '../userprofile/userprofile.component';




@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [RouterOutlet, MatDividerModule, CommonModule, UserprofileComponent,ComplaintComponent, AddroomComponent, RemoveroomComponent],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css',
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminDashboardComponent {
  

  selectedContent: string = 'home';
  constructor(private router: Router){}

  goToLogin(): void {
    this.router.navigate(['/admin-login']);
  }

  showContent(content: string): void {
    this.selectedContent = content;
  }

}
