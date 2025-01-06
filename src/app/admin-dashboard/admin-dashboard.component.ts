import { Component, CUSTOM_ELEMENTS_SCHEMA, Inject, PLATFORM_ID } from '@angular/core';
import { Router, RouterOutlet} from '@angular/router';
import { MatDividerModule } from '@angular/material/divider';
import { AddroomComponent } from '../addroom/addroom.component';
import { RemoveroomComponent } from '../removeroom/removeroom.component';
import { ComplaintComponent } from '../complaint/complaint.component';
import { FormsModule } from '@angular/forms';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { UserprofileComponent } from '../userprofile/userprofile.component';
import { ChatbotComponent } from '../chatbot/chatbot.component';


@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [RouterOutlet,FormsModule, MatDividerModule, ChatbotComponent, CommonModule, UserprofileComponent,ComplaintComponent, AddroomComponent, RemoveroomComponent],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css',
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminDashboardComponent {
  

  selectedContent: string = 'home';
  fullName: string | null = null;


  
  constructor(private router: Router, @Inject(PLATFORM_ID) private platformId: Object) {
    if (isPlatformBrowser(this.platformId)) {
      // Only access localStorage if running in the browser
      this.fullName = localStorage.getItem('fullName');
    }
  }

  goToLogin(): void {
    localStorage.removeItem('fullName');
    localStorage.removeItem('zid');
    this.router.navigate(['/admin-login']);
  }

  showContent(content: string): void {
    this.selectedContent = content;
  }

}
