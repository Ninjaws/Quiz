package com.ninjaws.quiz.services;

import org.springframework.stereotype.Service;

@Service
public class StateManagementService {

    /**
     * STARTUP: When starting the app
     * REFRESH: Scheduled at night, so the database stays fresh
     * NORMAL: No special activities
     */
    public enum ProcessingState {
        STARTUP,
        REFRESH,
        NORMAL
    }
    
    private ProcessingState currentProcessingState;
    
    public ProcessingState getCurrentProcessingState() {
        return currentProcessingState;
    }

    public void setCurrentProcessingState(ProcessingState currentProcessingState) {
        this.currentProcessingState = currentProcessingState;
    }
    
}
