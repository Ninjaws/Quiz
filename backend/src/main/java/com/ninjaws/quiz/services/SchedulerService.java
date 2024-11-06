package com.ninjaws.quiz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ninjaws.quiz.models.Request;

@Service
public class SchedulerService {

    @Autowired
    private QueueService queueService;
    @Autowired
    private ApiService apiService;
    @Autowired
    private StateManagementService stateService;

    public SchedulerService() {}

    /**
     * Every 5 seconds: 
     * - Check if there is a request in queue
     * - Yes: Send it to the apiService
     * - No: Have the apiService do bulk gathering for the DB
     */
    @Scheduled(fixedDelay = 5000)
    protected void processQueue() {
        Request request = queueService.popFromQueue();

        if (request != null) {
            apiService.handleRequest(request);
        } else {
            if (stateService.getCurrentProcessingState() == StateManagementService.ProcessingState.STARTUP || stateService.getCurrentProcessingState() == StateManagementService.ProcessingState.REFRESH) {
                apiService.collectBulk();
            }
        }
    }   

    /**
     * Starts the renewing of the questions, to ensure they stay fresh
     */
    @Scheduled(cron = "0 0 1 * * ?")
    protected void StartDbRefresh() {
        apiService.getSessionToken();
        stateService.setCurrentProcessingState(StateManagementService.ProcessingState.REFRESH);
    }
    
}
