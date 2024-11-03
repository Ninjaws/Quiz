package com.ninjaws.quiz.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ninjaws.quiz.services.ApiService;
import com.ninjaws.quiz.services.StateManagementService;


@Component
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private StateManagementService stateManagementService;
    @Autowired
    private ApiService apiService;

    @Override
    public void run(String... args) {

        /**
         * On startup:
         * - Set ProcessingState to Startup
         * - Store the categories
         * - Get a sessiontoken
         * 
         * * Inside the SchedulerService
         * - Start filling the question table
         * 
         * * Inside the Response Handling Service
         * - No more questions? Stop
         * - Set ProcessingState to Normal
         */

        stateManagementService.setCurrentProcessingState(StateManagementService.ProcessingState.STARTUP);
        apiService.getCategories();
        apiService.getSessionToken();
    }
}
