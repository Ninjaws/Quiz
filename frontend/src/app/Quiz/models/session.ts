import { Question } from "./question";

export interface Session {
    id: string;
    statusCode: number;
    questions: Question[];
}