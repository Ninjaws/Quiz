package com.ninjaws.quiz.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.ninjaws.quiz.models.Answers;
import com.ninjaws.quiz.models.ApiResponse;
import com.ninjaws.quiz.models.Question;
import com.ninjaws.quiz.models.QuizSettings;
import com.ninjaws.quiz.models.Request;
import com.ninjaws.quiz.models.Session;


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
     */
    // private final Map<String,Session> sessions = new ConcurrentHashMap<>();
    private final Cache<String,Session> sessions;

    /**
     * Stores all user requests for data
     * Executes them in order, one every 5 seconds (the limit of the external API)
     * Removes them from the Queue when the request was succesful
     */
    private final BlockingQueue<Request> requests = new LinkedBlockingQueue<>();


    public QuizService() {
        sessions = (Caffeine.newBuilder()
        .expireAfterWrite(1,TimeUnit.HOURS)
        .build());
    }

    public QuizService(Cache<String, Session> cache) {
        sessions = cache;
    }

    public QuizSettings jsonToQuizSettings(String json){
        QuizSettings quizSettings = new QuizSettings();
        return quizSettings;
    }

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
        try {
            requests.put(new Request(session.getId(), quizSettings));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

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
        Session session = sessions.getIfPresent(sessionId);

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
        System.out.print("Schedule!");
        Request request = requests.poll();
        if (request != null) {
            System.out.print("----------------An item!");
            String json = apiService.requestQuestions(request.getQuizSettings());
            System.out.print(json);
            try {
                List<Question> questions = extractQuestions(json);
                Session session = sessions.getIfPresent(request.getSessionId());
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
     * 
     * Actions: 
     * - retrieves session from cache
     * - Deletes session from cache
     * - Matches answers answers to correctAnswers
     * - Calculates score
     * Returns: calculated score
     *  */
    public String getScore(Answers answers) {
        Session session = sessions.getIfPresent(answers.getSessionId());
        sessions.invalidate(session.getId());
        return calculateScore(session, answers);
    }

    private String calculateScore(Session session, Answers answers) {
        System.out.print(session);
        System.out.print(answers);
        return "You did great!";
    }
    
}
