package com.ninjaws.quiz.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ninjaws.quiz.models.Answers;
import com.ninjaws.quiz.models.QuizSettings;
import com.ninjaws.quiz.models.Session;
import com.ninjaws.quiz.services.QuizService;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/questions")
    public String startSession(
        @RequestParam(required = true) Integer amount,
        @RequestParam(required = false) Integer category,
        @RequestParam(required = false) String difficulty,
        @RequestParam(required = false) String type
    ) {
        QuizSettings quizSettings = new QuizSettings(amount, category, difficulty, type);
        Optional<String> result = quizService.createSession(quizSettings);
        if(result.isPresent()) {
            String jsonString = String.format("{\"statusCode\":0,\"sessionId\":\"%s\"}", result.get());
            return jsonString;
        }
            String jsonString = String.format("{\"statusCode\":5}");
            return jsonString;
    }    

    @GetMapping("/status/{sessionId}")
    public Optional<Session> checkStatus(@PathVariable String sessionId) {
        return quizService.checkSession(sessionId);
    }

    @PostMapping("/score")
    public String checkAnswers(@RequestBody Answers answers) {
        String jsonString = String.format("{\"score\":\"%s\"}", quizService.getScore(answers));
        return jsonString;
    }
}
