import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Score } from '../models/score';

@Component({
  selector: 'app-score',
  standalone: true,
  imports: [],
  templateUrl: './score.component.html',
  styleUrl: './score.component.scss'
})
export class ScoreComponent {

  score !: Score;

  constructor(private router: Router) {
    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras?.state) {
      this.score = navigation.extras.state['score'];
    }else {
      // You didn't come here through the proper channels, byebye
      this.router.navigate(['']);
    }
  }

  playAgain() {
    this.router.navigate(['']);
  }
}
