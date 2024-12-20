import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { QuizService } from '../services/quiz.service';
import { QuizParams } from '../models/quiz-params';
import { SpinnerService } from '../../Spinner/services/spinner.service';
import { CommonModule } from '@angular/common';
import { Question } from '../models/question';
import { Category } from '../models/category';

@Component({
  selector: 'app-setup',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './setup.component.html',
  styleUrl: './setup.component.scss',
})
export class SetupComponent {

  categories!: Category[];
  category!: number;
  difficulty: string = "";
  type: string = "";
  amount: number = 10;

  constructor(
    private router: Router,
    public quizService: QuizService,
    private spinnerService: SpinnerService
  ) {
    quizService.getCategories().then((categories: Category[]) => {
      /** Setting default values */
      this.categories = categories;
      this.category = this.categories.find(item => item.id == 0)!.id;
      this.difficulty = this.quizService.getDifficulties()[0];
      this.type = this.quizService.getTypes()[0];      
    });
  }

  startQuiz() {
    const params: QuizParams = {
      category: this.category,
      type: this.type,
      amount: this.amount,
      difficulty: this.difficulty,
    };

    this.spinnerService.setActive(true);
    this.quizService.startSession(params).then((questions: Question[] | null) => {
      if(questions !== null) {
        this.gotoQuiz(questions);
      }
    })
    .catch((e) => {alert("Something went wrong, please try again");})
    .finally(() => {this.spinnerService.setActive(false);});

  }

  gotoQuiz(questions: Question[]) {
    this.router.navigate(['/quiz'], {
      state: { questions: questions },
      skipLocationChange: true,
    });
  }
}
