import { animate, style, transition, trigger } from '@angular/animations';
import { Component } from '@angular/core';
import { SpinnerService } from '../services/spinner.service';
import { CommonModule } from '@angular/common';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';

@Component({
  selector: 'app-spinner',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './spinner.component.html',
  styleUrl: './spinner.component.scss',
  styles: [], 
  animations:  [
    trigger('fadeInOut', [
      transition(':enter', [style({ opacity:0}), animate("300ms ease-in-out")]),
      transition(':leave', [animate("300ms ease-in-out", style({ opacity: 0}))]),
  ])]
})
export class SpinnerComponent {
  constructor(public spinnerService: SpinnerService) { }
}
