package com.ninjaws.quiz.models;

import lombok.Getter;

@Getter
public class Request {
    private final String sessionId;
    private final QuizSettings quizSettings;

    public Request(String sessionId, QuizSettings quizSettings) {
        this.sessionId = sessionId;
        this.quizSettings = quizSettings;
    }
}
