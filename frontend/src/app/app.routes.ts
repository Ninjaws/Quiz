import { Routes } from '@angular/router';

export const routes: Routes = [
    { path: '', loadChildren: () => import('./Quiz/quiz.module').then(m => m.QuizModule) }, // Lazy load QuizModule
    // { path: '**', redirectTo: '/quiz' } // Handle unmatched routes

];
