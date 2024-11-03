package com.ninjaws.quiz.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninjaws.quiz.entities.QuestionEntity;
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
    @Autowired
    private DataService dataService;
    @Autowired
    private CleanerService cleanerService;

    /**
     * Actions:
     * - Creates a session with sessionId
     * - Adds session to the cache
     * - Creates a request and adds it to the queue
     * @param quizSettings The values set by the user, determining the kind of quiz they want
     * @return The sessionId
     */
    public Optional<String> createSession(QuizSettings quizSettings) {
        Session session = new Session();

        /** First, we check if there are already enough items in our db */
        List<QuestionEntity> questionsRetrieved = dataService.lookupQuestions(quizSettings);
        if(!questionsRetrieved.isEmpty() && questionsRetrieved.size() == quizSettings.getAmount()) {
            session.setQuestions(cleanerService.questionListEntitytoDTO(questionsRetrieved));
        }

        cacheService.addToCache(session.getId(), session);

        /**
         * If the questions were retrieved from the DB, then it doesn't need to be added to the queue
         */
        if(!questionsRetrieved.isEmpty() && questionsRetrieved.size() == quizSettings.getAmount()) {
            return Optional.of(session.getId());
        }

        boolean addedToQueue = queueService.addToQueue(new Request(session.getId(), quizSettings));
        /** In this order, because doing the queue before the cache could lead to race conditions (request completion immediately calls cache, but then the item doesn't exist) */
        if(!addedToQueue) {
            cacheService.removeFromCache(session.getId());
            return Optional.empty();
        }

        return Optional.of(session.getId());
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

        /** Still waiting for a response */
        if (session.getQuestions() == null) {
            return Optional.empty();
        }

        /** Anything other than statuscode 0 doesn't result in active sessions, so remove them from the cache after reporting to the frontend */
        // if(session.getStatusCode() != 0) {
        /** Regardless of status code, this link is not reusable */
        cacheService.removeFromCache(session.getId());
        // }

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
            if (correctAnswer.equalsIgnoreCase(userAnswer)) {
                amountCorrect++;
            }
        }
        return amountCorrect;
    }    
}
