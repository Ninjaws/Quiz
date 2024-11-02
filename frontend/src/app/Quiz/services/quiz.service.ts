import { Injectable } from '@angular/core';
import { QuizParams } from '../models/quiz-params';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {
  catchError,
  filter,
  firstValueFrom,
  interval,
  lastValueFrom,
  of,
  startWith,
  switchMap,
  take,
  takeWhile,
} from 'rxjs';
import { Question } from '../models/question';
import { Answers } from '../models/answers';
import { StorageService } from './storage.service';
import { CleanerService } from './cleaner.service';
import { ResponseHandlerService } from './response-handler.service';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class QuizService {
  private apiUrl = environment.apiUrl + '/quiz';

  constructor(
    private http: HttpClient, 
    private storageService: StorageService, 
    private cleanerService: CleanerService, 
    private reponseHandler: ResponseHandlerService) {}

  public async startSession(quizParams: QuizParams): Promise<Question[] | null> {
    const sessionIdObj = await firstValueFrom(this.http.get<any>(this.apiUrl + '/questions' + this.convertParamsToUrl(quizParams)));
    /** No need to poll if the session is not created. Likely means the queue is full */
    if(sessionIdObj.statusCode !== 0) {
      return this.reponseHandler.handleResponse(sessionIdObj.statusCode);
    }
    this.storageService.setSession(sessionIdObj.sessionId);
    const sessionObj = await lastValueFrom(this.pollQuestions(sessionIdObj.sessionId));

    return this.reponseHandler.handleResponse(sessionIdObj.statusCode, sessionObj);
  }

  public pollQuestions(sessionId: string) {
    return interval(2000).pipe(
      startWith(0),
      switchMap(() => this.http.get<any>(this.apiUrl + `/status/${sessionId}`)),
      // takeWhile((response) => response === null),
      filter((response) => response !== null),
      take(1)
    );
  }

  public async getScore(answers: string[]) {
    const answersJson: Answers = {
      sessionId: this.storageService.getSession()!,
      answers: answers
    }

    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    const scoreObj = await firstValueFrom(this.http.post<any>(this.apiUrl+'/score', answersJson, { headers }));
    return scoreObj.score;
  }

  private convertParamsToUrl(quizParams: QuizParams) {
    let url = `?amount=${quizParams.amount}`;
    const catIndex = this.getCategories().indexOf(quizParams.category);
    url += catIndex !== 0 ? `&category=${catIndex}` : '';

    const diff = quizParams.difficulty.toLowerCase();
    url += diff !== 'any difficulty' ? `&difficulty=${diff}` : '';

    const typeIndex = this.getTypes().indexOf(quizParams.type);
    url +=
      typeIndex !== 0
        ? typeIndex === 1
          ? '&type=multiple'
          : '&type=boolean'
        : '';
    return url;
  }

  public getCategories(): string[] {
    return [
      'Any Category',
      'General Knowledge',
      'Entertainment: Books',
      'Entertainment: Film',
      'Entertainment: Music',
      'Entertainment: Musicals & Theatres',
      'Entertainment: Television',
      'Entertainment: Video Games',
      'Entertainment: Board Games',
      'Science & Nature',
      'Science: Computers',
      'Science: Mathematics',
      'Mythology',
      'Sports',
      'Geography',
      'History',
      'Politics',
      'Art',
      'Celebrities',
      'Animals',
      'Vehicles',
      'Entertainment: Comics',
      'Science: Gadgets',
      'Entertainment: Japanese Anime & Manga',
      'Entertainment: Cartoon & Animations',
    ];
  }

  public getDifficulties(): string[] {
    return ['Any Difficulty', 'Easy', 'Medium', 'Hard'];
  }

  public getTypes(): string[] {
    return ['Any Type', 'Multiple choice', 'True/False'];
  }
}
