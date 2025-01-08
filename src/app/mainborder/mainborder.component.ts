import { Component, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MatDividerModule } from '@angular/material/divider';
import { Router, RouterOutlet } from '@angular/router';
import { UserService } from '../user.service';
import { CommonModule } from '@angular/common';
import { UserprofileComponent } from '../userprofile/userprofile.component';

import { ChatbotComponent } from '../chatbot/chatbot.component';
import { GreivanceComponent } from '../greivance/greivance.component';
import { DropdownComponent } from '../dropdown/dropdown.component';
import { UserpasthistoryComponent } from '../userpasthistory/userpasthistory.component';
import { UsercurrenthistoryComponent } from '../usercurrenthistory/usercurrenthistory.component';




@Component({
  selector: 'app-mainborder',
  standalone: true,
  imports: [MatDividerModule, RouterOutlet, CommonModule, UserpasthistoryComponent, DropdownComponent, UsercurrenthistoryComponent, UserprofileComponent, ChatbotComponent, GreivanceComponent],
  templateUrl: './mainborder.component.html',
  styleUrls: ['./mainborder.component.css'],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class MainborderComponent {

  selectedContent: string = 'home';

  constructor(private router: Router, private userService: UserService) {}

  get fullName(): string {
    return this.userService.getFullName();
  }

  goToLogin(): void {
    this.router.navigate(['/login']);
  }

  showContent(content: string): void {
    this.selectedContent = content;
  }
}
