import { Category } from "./category";

export interface QuizParams {
    category: number;
    type: string;
    amount: number;
    difficulty: string;
  }