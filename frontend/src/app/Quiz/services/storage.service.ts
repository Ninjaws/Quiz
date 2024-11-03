import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root',
  })
  export class StorageService {
    constructor() {
    }

    public setSession(sessionId: string): void {
        localStorage.setItem("sessionId", sessionId);
    }

    public getSession(): string | null {
        return localStorage.getItem("sessionId");
    }
}