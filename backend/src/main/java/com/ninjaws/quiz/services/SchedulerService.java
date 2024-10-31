package com.ninjaws.quiz.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaws.quiz.models.ApiResponse;
import com.ninjaws.quiz.models.Question;
import com.ninjaws.quiz.models.Request;
import com.ninjaws.quiz.models.Session;

@Service
public class SchedulerService {

    @Autowired
    private QueueService queueService;
    @Autowired
    private ApiService apiService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private ObjectMapper objectMapper;

    private static final System.Logger logger = System.getLogger(SchedulerService.class.getName());

    public SchedulerService() {}

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
        Request request = queueService.popFromQueue();
        if (request != null) {
            String json = apiService.requestQuestions(request.getQuizSettings());
            try {
                List<Question> questions = extractQuestions(json);
                Session session = cacheService.getFromCache(request.getSessionId());
                session.setQuestions(questions);
                cacheService.addToCache(request.getSessionId(), session);
            } catch (IOException | NullPointerException e) {
                logger.log(System.Logger.Level.ERROR, "Critical failure", e);
            }
        }
    }

    private List<Question> extractQuestions(String json) throws IOException {
        return objectMapper.readValue(json, ApiResponse.class).getResults();
    }
}
