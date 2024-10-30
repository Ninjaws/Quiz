package com.ninjaws.quiz.services;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaws.quiz.models.QuizSettings;

@SpringBootTest
public class ApiServiceTest {

    @Autowired
    private ApiService apiService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * The external API only allows one request every 5 seconds
     */
    @AfterEach
    public void cooldown() throws InterruptedException {
        Thread.sleep(5000);
    }

    @Test
    public void testRequestQuestions() throws Exception {
        QuizSettings settings = new QuizSettings();
        String jsonResponse = apiService.requestQuestions(settings);
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        // Check if the response code is as expected
        assertThat(jsonNode.has("response_code")).isTrue();
        assertThat(jsonNode.get("response_code").asInt()).isEqualTo(0);

        // Check the results array
        assertThat(jsonNode.has("results")).isTrue();
        assertThat(jsonNode.get("results").isArray()).isTrue();
        assertThat(jsonNode.get("results").size()).isEqualTo(10);

        // Verify the first result has the expected fields
        JsonNode firstResult = jsonNode.get("results").get(0);
        assertThat(firstResult.has("type")).isTrue();
        assertThat(firstResult.has("difficulty")).isTrue();
        assertThat(firstResult.has("category")).isTrue();
        assertThat(firstResult.has("question")).isTrue();
        assertThat(firstResult.has("correct_answer")).isTrue();
        assertThat(firstResult.has("incorrect_answers")).isTrue();
        assertThat(firstResult.get("incorrect_answers").size()).isGreaterThan(0);
    }

    @Test
    public void testAllParams() throws Exception {
        int amount = 10;

        QuizSettings settings = new QuizSettings();
        settings.setAmount(amount);
        settings.setCategory(QuizSettings.Category.HISTORY);
        settings.setType(QuizSettings.Type.MULTIPLE);
        settings.setDifficulty(QuizSettings.Difficulty.MEDIUM);

        String jsonResponse = apiService.requestQuestions(settings);
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        // Check if the response code is as expected
        assertThat(jsonNode.has("response_code")).isTrue();
        assertThat(jsonNode.get("response_code").asInt()).isEqualTo(0);

        // Check the results array
        assertThat(jsonNode.has("results")).isTrue();
        assertThat(jsonNode.get("results").isArray()).isTrue();
        assertThat(jsonNode.get("results").size()).isEqualTo(amount);

        // Verify the first result has the expected fields
        JsonNode firstResult = jsonNode.get("results").get(0);
        String retrievedType = firstResult.get("type").toString();
        String retrievedDifficulty = firstResult.get("difficulty").toString();
        String retrievedCategory = firstResult.get("category").toString();
        assertThat(retrievedType.equals(settings.getType().getApiValue()));
        assertThat(retrievedDifficulty.equals(settings.getDifficulty().getApiValue()));
        assertThat(retrievedCategory.equals("Animals"));
        assertThat(firstResult.get("incorrect_answers").size()).isEqualTo(3);
    }

    /**
     * If the API doesn't have the requested amount of questions, it will return an empty list and respond with 1
     */
    @Test
    public void testCombinationThatResultsInZeroQuestions() throws Exception {
        int amount = 30;

        QuizSettings settings = new QuizSettings();
        settings.setAmount(amount);
        settings.setCategory(QuizSettings.Category.ANIMALS);
        settings.setType(QuizSettings.Type.MULTIPLE);
        settings.setDifficulty(QuizSettings.Difficulty.MEDIUM);

        String jsonResponse = apiService.requestQuestions(settings);
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        // Check if the response code is as expected
        assertThat(jsonNode.has("response_code")).isTrue();
        assertThat(jsonNode.get("response_code").asInt()).isEqualTo(1);

        // Check the results array
        assertThat(jsonNode.has("results")).isTrue();
        assertThat(jsonNode.get("results").isArray()).isTrue();
        assertThat(jsonNode.get("results").size()).isEqualTo(0);
    }
}