package com.ninjaws.quiz.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.ninjaws.quiz.models.ApiResponse;
import com.ninjaws.quiz.models.QuizSettings;
import com.ninjaws.quiz.models.Request;

import jakarta.validation.Valid;

@Service
public class ApiService {

    @Autowired
    private CleanerService cleanerService;
    @Autowired
    private ResponseHandlingService responseHandler;
    @Autowired
    private RestTemplate restTemplate;

    private static final System.Logger logger = System.getLogger(ApiService.class.getName());

    private final String apiUrl = "https://opentdb.com/api.php";

    public ApiService() {
    }

    private String buildRequestString(QuizSettings quizSettings) {
        return apiUrl + quizSettings.toApiQuery();
    }

    public void handleRequest(@Valid Request request) {
        String url = buildRequestString(request.getQuizSettings());

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String json = response.getBody();
            ApiResponse apiResponse = cleanerService.extractApiResponse(json);
            responseHandler.handleResponse(request.getSessionId(), apiResponse);
            
        } catch (RestClientException | IOException | IllegalStateException e) {
            logger.log(System.Logger.Level.ERROR, "Critical failure", e);
        }
    }
}
