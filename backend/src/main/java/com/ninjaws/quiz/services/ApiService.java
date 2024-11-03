package com.ninjaws.quiz.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.ninjaws.quiz.models.ApiResponse;
import com.ninjaws.quiz.models.CategoryResponse;
import com.ninjaws.quiz.models.QuizSettings;
import com.ninjaws.quiz.models.Request;
import com.ninjaws.quiz.models.SessionTokenDTO;

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

    private final String apiUrl = "https://opentdb.com";
    private String sessionToken;

    public ApiService() {
    }

    private String buildRequestString(QuizSettings quizSettings) {
        return apiUrl + "/api.php" + quizSettings.toApiQuery();
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

    /**
     * Collect a chuck of 50 items
     * Store them in the DataService
     */
    public void collectBulk() {
        String tokenStr = sessionToken == null ? "" : "&token="+sessionToken;
        String url = apiUrl + "/api.php?amount=50" + tokenStr;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String json = response.getBody();
            ApiResponse apiResponse = cleanerService.extractApiResponse(json);
            responseHandler.handleResponse(apiResponse);
            
            /** 
             * A bit dirty, but otherwise it causes circular dependencies 
             * Makes sure the categories are refreshed after finishing to replace the questions, to make sure the ids match
             * And resets the sessionToken, it will be remade during the next REFRESH
            */
            if(apiResponse.getResponseCode() == 4) {
                getCategories();
                sessionToken = null;
            }
            
        } catch (RestClientException | IOException e) {
            logger.log(System.Logger.Level.ERROR, "Critical failure", e);
        }

    }

    public void getSessionToken() {
        String url = apiUrl + "/api_token.php?command=request";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String json = response.getBody();
            SessionTokenDTO tokenDto = cleanerService.extractSessionToken(json);
            sessionToken = tokenDto.getToken();
            
        } catch (RestClientException | IOException e) {
            logger.log(System.Logger.Level.ERROR, "Critical failure", e);
        }
    }

    public void getCategories() {
        String url = apiUrl + "/api_category.php";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String json = response.getBody();
            CategoryResponse categoryResponse = cleanerService.extractCategoryResponse(json);
            responseHandler.handleCategoryResponse(categoryResponse);
            
        } catch (RestClientException | IOException e) {
            logger.log(System.Logger.Level.ERROR, "Critical failure", e);
        }

    }
}
