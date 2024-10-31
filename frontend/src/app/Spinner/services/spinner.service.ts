import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SpinnerService {

  private isActive = false;
  constructor() { }

  toggleSpinner() {
    this.isActive = !this.isActive;
  }

  setActive(active: boolean) {
    new Promise(
      (resolve) => setTimeout(resolve, 0)).then(
        _ =>this.isActive = active
    )
  }

  isSpinnerActive() {
    return this.isActive;
  }
}
