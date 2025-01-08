import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-chatbot',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chatbot.component.html',
  styleUrl: './chatbot.component.css'
})
export class ChatbotComponent {
  userInput: string = '';
  messages: { from: string, text: string }[] = [];

  sendMessage() {
    if (this.userInput.trim()) {
      // Add user message to the chat
      this.messages.push({ from: 'user', text: this.userInput });

      // Generate a bot response (this can be expanded to more dynamic logic)
      const botResponse = this.getBotResponse(this.userInput);
      this.messages.push({ from: 'bot', text: botResponse });

      // Clear user input
      this.userInput = '';
    }
  }
  getBotResponse(userMessage: string): string {
    // Define the responses with a more general key type
    const responses: Record<string, string> = {
      "hello": "Hi! How can I assist you today?",
      "help": "Sure! What do you need help with?",
      "what is meeting room": "A meeting room is a designated space where individuals gather to conduct discussions, conferences, or collaborative work. It is typically equipped with furniture such as tables and chairs, and often includes technology for presentations, video conferencing, or communication tools like projectors, whiteboards, and microphones. Meeting rooms can be found in various settings such as offices, conference centers, hotels, and other venues. These rooms are designed to provide a professional environment for meetings, whether for business, team collaboration, or other organizational purposes.Meeting rooms vary in size depending on the number of people they are meant to accommodate, and they can be formal or informal, depending on the nature of the gathering",
      "used for": "Meeting rooms are used for business meetings, collaborative work, presentations, training, conferences, interviews, video calls, and decision-making.",
      "about this application": "A meeting room booking application is a software tool designed to help organizations manage the scheduling and booking of meeting rooms within a facility. This application simplifies the process of reserving rooms, ensures optimal use of available spaces, and streamlines the overall scheduling process. Would you like more details on a specific feature or help with something else?",
      "thank you": "You're welcome! Let me know if you need anything else.",
      "bye": "Goodbye! Have a great day. Incase any query connect with me again!"
    };
  
    // Default error message
    const error = "I'm sorry, I didn't quite understand that. Can you please clarify?";
  
    // Convert the message to lowercase and find the response, or return the error message
    const lowerCaseMessage = userMessage.toLowerCase();
    return responses[lowerCaseMessage] || error;
  }

}
