import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Score } from '../models/score';
import { Question } from '../models/question';
import { QuizService } from '../services/quiz.service';

@Component({
  selector: 'app-quiz',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './quiz.component.html',
  styleUrl: './quiz.component.scss'
})
export class QuizComponent {
  questions: Question[] = [];
  selectedAnswers: { [key: number]: string } = {};

  constructor(private router: Router, private quizService: QuizService) {
    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras?.state) {
      this.questions = navigation.extras.state['questions'];
    }else {
      // You didn't come here through the proper channels, byebye
      this.router.navigate(['']);
    }
  }

  selectAnswer(questionIndex: number, answer: string) {
    this.selectedAnswers[questionIndex] = answer;
  }

  allQuestionsAnswered(): boolean {
    return Object.keys(this.selectedAnswers).length === this.questions.length; 
  }

  getScore() {
    const answers: string[] = []
    for(let i = 0; i < Object.keys(this.selectedAnswers).length; i++) {
      answers.push(this.selectedAnswers[i]);
    }
    this.quizService.getScore(answers)
    .then((result:number) => {
      const score: Score = {
        amount_correct:result,
        amount_questions: Object.keys(this.selectedAnswers).length
      }
      this.router.navigate(['/score'], { state: { score: score },skipLocationChange:true });
    })
    .catch((e) => {
      alert("Something went wrong");
    });

    // const sampleScore: Score = {
    //   amount_questions: 10,
    //   amount_correct: 7
    // }

  }
}