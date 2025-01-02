import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { RouterLink  } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { MainborderComponent } from './mainborder/mainborder.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, HomeComponent, MainborderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Request-meeting';


}
