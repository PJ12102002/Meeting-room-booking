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
      "thank you": "You're welcome! Let me know if you need anything else.",
      "bye": "Goodbye! Have a great day."
    };
  
    // Default error message
    const error = "I'm sorry, I didn't quite understand that. Can you please clarify?";
  
    // Convert the message to lowercase and find the response, or return the error message
    const lowerCaseMessage = userMessage.toLowerCase();
    return responses[lowerCaseMessage] || error;
  }

}
