import { Injectable } from '@angular/core';
import { Session } from '../models/session';
import { CleanerService } from './cleaner.service';
import { Question } from '../models/question';

@Injectable({
  providedIn: 'root',
})
export class ResponseHandlerService {
  constructor(private cleanerService: CleanerService) {}

  /**
   * Session is optional, except for the success state (0)
   */
  public handleResponse(statusCode: number, session?: Session): Question[] | null {
    switch (statusCode) {
      case 0:
        // Success
        session!.questions = this.cleanerService.cleanQuestionStrings(session!.questions);
        return session!.questions;
      case 1:
        // No Results (requests too much data for this combination of parameters)
        alert('Not enough data for this request, please try a different combination');
        return null;
      case 2:
        // Invalid parameter (should be handled by the backend)
        alert('Invalid input');
        return null;
      case 5:
        // Queue is full, try again later
        alert('The server is experiencing a lot of traffic, please try again later');
        return null;
    }
    return null;
  }
}