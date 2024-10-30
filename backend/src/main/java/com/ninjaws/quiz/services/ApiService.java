package com.ninjaws.quiz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ninjaws.quiz.models.QuizSettings;

import jakarta.validation.Valid;

@Service
public class ApiService {

    // @Autowired
    // private ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    private final String apiUrl = "https://opentdb.com/api.php"; 

    // private String sessionToken = "";

    @Autowired
    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        // try {
        //     sessionToken = requestSessionToken();
        // } catch (Exception e) {
        //     // Failed to get a session token, setting default to none
        //     sessionToken = "";

        // }
    }

    private String buildRequestString(QuizSettings quizSettings) {
        return apiUrl + quizSettings.toApiQuery();
    }

    // private int getResponseCode(String json) throws Exception {
    //     JsonNode jsonNode = objectMapper.readTree(json);
    //     return jsonNode.get("response_code").asInt();
    // }

    // public String requestSessionToken() throws Exception {
    //     String sessionTokenRequestUrl = "https://opentdb.com/api_token.php?command=request";
    //     ResponseEntity<String> response = restTemplate.getForEntity(sessionTokenRequestUrl, String.class);
    //     JsonNode jsonNode = objectMapper.readTree(response.getBody());
    //     if (jsonNode.get("response_code").asInt() == 0) {
    //         sessionToken = jsonNode.get("token").asText();
    //     }else {
    //         // Failed to get a session token, setting default to none
    //         sessionToken = "";
    //     }
    //     return sessionToken;
    // }

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
}
