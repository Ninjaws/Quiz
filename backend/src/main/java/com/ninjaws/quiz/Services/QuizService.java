package com.ninjaws.quiz.Services;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaws.quiz.Models.ApiResponse;
import com.ninjaws.quiz.Models.Question;
import com.ninjaws.quiz.Models.Request;
import com.ninjaws.quiz.Models.Session;


@Service
public class QuizService {

    @Autowired
    private ApiService apiService;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Stores all active sessions
     * Gets added to when a user requests data
     * Gets removed from when a user requests their score (by sending their answers)
     * TODO: Replace with something that automatically removes items after a certain amount of time (Caffein?)
     */
    private final Map<String,Session> sessions = new ConcurrentHashMap<>();

    /**
     * Stores all user requests for data
     * Executes them in order, one every 5 seconds (the limit of the external API)
     * Removes them from the Queue when the request was succesful
     */
    private final BlockingQueue<Request> requests = new LinkedBlockingQueue<>();

    /**
     * Actions:
     * - Creates a session with sessionId
     * - Adds session to the cache
     * - Creates request and adds it to the queue
     * @return The sessionId
     */
    public String createSession() {
        Session session = new Session();
        sessions.put(session.getId(), session); // TODO: Either remove id from session, or maybe Caffein does allow them together
        try {
            requests.put(new Request(session.getId(), "https://opentdb.com/api.php?amount=10"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return session.getId();
    }

    /**
     * @param sessionId
     * Actions:
     * - Looks up the session in the cache
     * - Checks if the data from the request is stored
     * @return The data if it's available, otherwise a statuscode (?)
     */
    public Optional<Session> checkSession(String sessionId) throws IllegalArgumentException {
        Session session = sessions.get(sessionId);

        if(session == null) {
            throw new IllegalArgumentException("Invalid session ID: " + sessionId);
        }

        if (session.getQuestions() == null) {
            return Optional.empty();
        }
        System.out.print(session.getQuestions().get(0).getIncorrectAnswers().get(0));
        return Optional.of(session);
    }

    /**
     * Every 5 seconds:
     * - Take the top request from the queue (removes it)
     * - Perform the request
     * - Wait for the data
     * - Find the matching session from the cache
     * - Add the data to the session in the cache
     */
    @Scheduled(fixedDelay = 5000)
    protected void processQueue() {
        Request request = requests.poll();
        if (request != null) {
            String json = apiService.requestQuestions(request.getRequestUrl());
            try {
                List<Question> questions = extractQuestions(json);
                Session session = sessions.get(request.getSessionId());
                session.setQuestions(questions);
                sessions.put(request.getSessionId(), session);                
            } catch (IOException e) {
                //TODO: Log this
            }
        }
    }

    private List<Question> extractQuestions(String json) throws IOException {
        return objectMapper.readValue(json, ApiResponse.class).getResults();
    }

    /**
     * Receives: sessionId and answers
     * Actions: 
     * - retrieves session from cache
     * - Deletes session from cache
     * - Matches answers answers to correctAnswers
     * - Calculates score
     * Returns: calculated score
     *  */
    public void calculateScore() {
        
    }
    
}
