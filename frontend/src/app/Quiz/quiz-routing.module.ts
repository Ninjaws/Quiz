import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SetupComponent } from './setup/setup.component';
import { QuizComponent } from './quiz/quiz.component';
import { ScoreComponent } from './score/score.component';

const routes: Routes = [
  { path: '', component: SetupComponent },
  { path: 'quiz', component: QuizComponent },
  { path: 'score', component: ScoreComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QuizRoutingModule { }
