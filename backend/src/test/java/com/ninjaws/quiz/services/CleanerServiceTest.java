package com.ninjaws.quiz.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaws.quiz.models.ApiResponse;
import com.ninjaws.quiz.models.Question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CleanerServiceTest {

    @InjectMocks
    private CleanerService cleanerService;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExtractApiResponse() throws IOException {
        String json = "{\"response_code\":200,\"results\":[{\"type\":\"multiple choice\",\"difficulty\":\"easy\",\"category\":\"general knowledge\",\"question\":\"What is the capital of France?\",\"correct_answer\":\"Paris\",\"incorrect_answers\":[\"Berlin\",\"Madrid\",\"Rome\"]}]}"; // Example JSON input
       
        Question question = new Question();
        question.setType("multiple choice");
        question.setDifficulty("easy");
        question.setCategory("general knowledge");
        question.setQuestion("What is the capital of France?");
        question.setCorrectAnswer("Paris");
        question.setIncorrectAnswers(Arrays.asList("Berlin", "Madrid", "Rome"));

        ApiResponse expectedResponse = new ApiResponse();
        expectedResponse.setResponseCode(200);
        expectedResponse.setResults(Arrays.asList(question));
        when(objectMapper.readValue(json, ApiResponse.class)).thenReturn(expectedResponse);
        
        ApiResponse actualResponse = cleanerService.extractApiResponse(json);
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getResponseCode()).isEqualTo(expectedResponse.getResponseCode());
        assertThat(actualResponse.getResults()).isNotEmpty();
        assertThat(actualResponse.getResults().get(0).getQuestion()).isEqualTo(question.getQuestion());
        verify(objectMapper, times(1)).readValue(json, ApiResponse.class);
    }
}