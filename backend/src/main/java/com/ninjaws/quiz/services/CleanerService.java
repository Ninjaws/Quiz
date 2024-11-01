package com.ninjaws.quiz.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaws.quiz.models.ApiResponse;

@Service
public class CleanerService {

    @Autowired
    private ObjectMapper objectMapper;

    public CleanerService() {}

    public ApiResponse extractApiResponse(String json) throws IOException {
        return objectMapper.readValue(json, ApiResponse.class);
    }
}
