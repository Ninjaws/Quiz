export interface Question {
  question: string;
  answers: string[];
  category: string;
  type: string;
  difficulty: string;

  /** To make special characters visible */
  question_decoded?: string;
  answers_decoded?: string[];
}
