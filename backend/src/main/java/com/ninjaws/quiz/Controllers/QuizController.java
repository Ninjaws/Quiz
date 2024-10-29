package com.ninjaws.quiz.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ninjaws.quiz.Models.Session;
import com.ninjaws.quiz.Services.QuizService;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/questions")
    public String startSession() {
        // String sessionId = generateSessionId();
        String sessionId = quizService.createSession();
        return sessionId;
    }

    @GetMapping("/questions/{sessionId}")
    public Optional<Session> checkStatus(@PathVariable String sessionId) {
        return quizService.checkSession(sessionId);
    }

    // @PostMapping("/checkanswers")
    // public Result checkAnswers(@RequestBody String answers) {
    //     return new Result();
    // }
}
