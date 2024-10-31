package com.ninjaws.quiz.services;

import org.springframework.stereotype.Service;

import com.ninjaws.quiz.models.QuizSettings;

@Service
public class CleanerService {

    public CleanerService() {}

    public QuizSettings jsonToQuizSettings(String json){
        QuizSettings quizSettings = new QuizSettings();
        return quizSettings;
    }
}
