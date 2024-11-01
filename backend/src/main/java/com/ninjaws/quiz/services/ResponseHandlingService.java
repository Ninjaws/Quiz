package com.ninjaws.quiz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninjaws.quiz.models.ApiResponse;
import com.ninjaws.quiz.models.Session;

@Service
public class ResponseHandlingService {

    @Autowired
    private CacheService cacheService;

    /**
     * If there is no sessionId, then this is a request from the scheduler
     * purely to stock in the database No Cache call is required
     */
    public void handleResponse(String sessionId, ApiResponse response) throws IllegalStateException {

        Session session = cacheService.getFromCache(sessionId);
        if (session == null) {
            // Critical system failure: Should never occur
            throw new IllegalStateException("Session should be defined!");
        }

        switch (response.getResponseCode()) {
            case 0:
                // Nothing
                updateSessionInCache(session, response);
                break;
            case 1:
                // Not enough questions to satisfy the request. 
                updateSessionInCache(session, response);
                break;
            case 2:
                // Invalid parameters
                updateSessionInCache(session, response);
                break;
            case 3:
                // If it expired. Shouldn't happen since the backup database is full within 40 minutes, and the code lasts for 6 hours
                // Return a statuscode 1 to signal an issue, just in case a user hits this situation (should not happen)
                session.setStatusCode(1);
                updateSessionInCache(session, response);
            case 4:
                // It returns nothing, the previous batch was the last one
                // Return a statuscode 1 to the user
                session.setStatusCode(1);
                updateSessionInCache(session, response);
                break;
            case 5:
                // Should be prevented by the scheduler
            default:
                break;
        }
    }

    /**
     * If the request was made by the scheduler during queue-downtime, there is no session
     */
    public void switchInternal(ApiResponse response) {
        switch (response.getResponseCode()) {
            case 0:
                // TODO: Store in DB
                break;
            case 4:
                // Signal DataService that we have extracted all the data. Stop gathering, switch to caching-mode
                // TODO: Signal DB that it's full, start db-swapping process
                break;
            case 5:
                // Should be prevented by the scheduler
            default:
                break;
        }
    }

    private void updateSessionInCache(Session session, ApiResponse response) {
        session.setStatusCode(response.getResponseCode());
        session.setQuestions(response.getResults());
        cacheService.addToCache(session.getId(), session);
    }

}
