package com.ninjaws.quiz.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninjaws.quiz.models.Answers;
import com.ninjaws.quiz.models.QuizSettings;
import com.ninjaws.quiz.models.Request;
import com.ninjaws.quiz.models.Session;


@Service
public class QuizService {

    @Autowired
    private CacheService cacheService;
    @Autowired
    private QueueService queueService;

    /**
     * Actions:
     * - Creates a session with sessionId
     * - Adds session to the cache
     * - Creates a request and adds it to the queue
     * @param quizSettings The values set by the user, determining the kind of quiz they want
     * @return The sessionId
     */
    public String createSession(QuizSettings quizSettings) {
        Session session = new Session();
        cacheService.addToCache(session.getId(), session);
        queueService.addToQueue(new Request(session.getId(), quizSettings));

        return session.getId();
    }

    /**
     * Actions:
     * - Looks up the session in the cache
     * - Checks if the data from the request is stored
     * @param sessionId
     * @return The data if it's available, Optional.empty() if not, and an Exception if the session is invalid
     */
    public Optional<Session> checkSession(String sessionId) throws IllegalArgumentException {
        Session session = cacheService.getFromCache(sessionId);

        if(session == null) {
            throw new IllegalArgumentException("Invalid session ID: " + sessionId);
        }

        if (session.getQuestions() == null) {
            return Optional.empty();
        }
        return Optional.of(session);
    }

    /**
     * Receives: sessionId and answers
     * 
     * Actions: 
     * - retrieves session from cache
     * - Deletes session from cache
     * - Matches answers answers to correctAnswers
     * - Calculates score
     * Returns: calculated score
     *  */
    public int getScore(Answers answers) {
        Session session = cacheService.getFromCache(answers.getSessionId());
        cacheService.removeFromCache(session.getId());
        return calculateScore(session, answers);
    }

    private int calculateScore(Session session, Answers answers) {
        int amountCorrect = 0;
        for(int i = 0; i < session.getQuestions().size(); i++) {
            String correctAnswer = session.getQuestions().get(i).getCorrectAnswer();
            String userAnswer = answers.getAnswers().get(i);
            // for(String answer : session.getQuestions().get(i).getAnswers()) {
            //     System.out.print(answer);
            // }
            
            if (correctAnswer.equalsIgnoreCase(userAnswer)) {
                amountCorrect++;
            }
        }
        return amountCorrect;
    }    
}
