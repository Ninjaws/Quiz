import { Injectable } from '@angular/core';
import { Question } from '../models/question';

@Injectable({
  providedIn: 'root'
})
export class CleanerService {

  constructor() { }

  /**
   * Sets decoded strings for the questions and the answers, so the special characters will be shown instead of their codes
   */
  public cleanQuestionStrings(questions: Question[]): Question[] {
    for(let i = 0; i < questions.length; i++) {
      questions[i].question_decoded = this.decodeString(questions[i].question);
      questions[i].answers_decoded = [];
      for(let j = 0; j < questions[i].answers.length; j++){
        questions[i].answers_decoded!.push(this.decodeString(questions[i].answers[j]));
      }
    }
    return questions;
  }

  /** Puts special characters back into the string, like ' and " */
  private decodeString(text: string) {
    const txt = document.createElement("textarea");
    txt.innerHTML = text;
    const decodedValue = txt.value;
    txt.remove();  // Clean up the temporary element
    return decodedValue;
  }
}
