package com.ninjaws.quiz.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    @Autowired
    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String requestQuestions(String url) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String jsonData = response.getBody();
        return jsonData;
    }
}
