package com.ninjaws.quiz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ninjaws.quiz.models.QuizSettings;

import jakarta.validation.Valid;

@Service
public class ApiService {

    @Autowired
    private RestTemplate restTemplate;

    private final String apiUrl = "https://opentdb.com/api.php"; 

    public ApiService() {
    }

    private String buildRequestString(QuizSettings quizSettings) {
        return apiUrl + quizSettings.toApiQuery();
    }

    public String requestQuestions(@Valid QuizSettings quizSettings) {
        String url = buildRequestString(quizSettings);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String jsonData = response.getBody();

        // try {
        //     int responseCode = getResponseCode(jsonData);
        //     /** When the code has expired or you've ran out of questions, update the token and try again */
        //     if(responseCode == 3 || responseCode == 4) {
        //         requestSessionToken();
        //         requestQuestions(quizSettings);
        //     }            
        // } catch (Exception e) {
        //     // Failed to get a responseCode, meaning the request failed completely (they always send a response_code)
        // }
        return jsonData;
    }

    public void handleStatusCode(int statusCode) {

    }
}
