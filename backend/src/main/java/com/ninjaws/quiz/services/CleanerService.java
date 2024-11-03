package com.ninjaws.quiz.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaws.quiz.entities.Category;
import com.ninjaws.quiz.entities.QuestionCollectingEntity;
import com.ninjaws.quiz.entities.QuestionEntity;
import com.ninjaws.quiz.models.ApiResponse;
import com.ninjaws.quiz.models.CategoryResponse;
import com.ninjaws.quiz.models.QuestionDTO;
import com.ninjaws.quiz.models.SessionTokenDTO;
import com.ninjaws.quiz.repositories.CategoryRepository;

/**
 * Deals with conversions from one form of data to another
 * Json to Object
 * Entity to DTO
 * etc
 */
@Service
public class CleanerService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    public CleanerService() {
    }

    public ApiResponse extractApiResponse(String json) throws IOException {
        return objectMapper.readValue(json, ApiResponse.class);
    }

    public CategoryResponse extractCategoryResponse(String json) throws IOException {
        return objectMapper.readValue(json, CategoryResponse.class);
    }

    public List<QuestionDTO> questionListEntitytoDTO(List<QuestionEntity> entities) {
        List<QuestionDTO> dtos = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            QuestionEntity entity = entities.get(i);
            dtos.add(questionEntityToDTO(entity));
        }
        return dtos;
    }

    public SessionTokenDTO extractSessionToken(String json) throws IOException {
        return objectMapper.readValue(json, SessionTokenDTO.class);
    }

    public QuestionDTO questionEntityToDTO(QuestionEntity entity) {
        QuestionDTO dto = new QuestionDTO();
        dto.setQuestion(entity.getQuestion());
        dto.setCorrectAnswer(entity.getCorrectAnswer());
        dto.setIncorrectAnswer(entity.getIncorrectAnswers());
        dto.setCategory(entity.getCategory().getName());
        dto.setType(entity.getType());
        dto.setDifficulty(entity.getDifficulty());
        return dto;
    }

    public List<QuestionEntity> questionListDTOtoEntity(List<QuestionDTO> dtos) {
        List<QuestionEntity> entities = new ArrayList<>();
        for (int i = 0; i < dtos.size(); i++) {
            QuestionDTO dto = dtos.get(i);
            entities.add(questionDTOtoEntity(dto));
        }
        return entities;
    }
   
    public QuestionEntity questionDTOtoEntity(QuestionDTO dto) {
        QuestionEntity entity = new QuestionEntity();
        Category category = categoryRepository.findByName(decodeString(dto.getCategory())).orElseThrow(() -> new IllegalStateException("Category not in DB!"));
        entity.setCategory(category);
        entity.setDifficulty(dto.getDifficulty());
        entity.setType(dto.getType());
        entity.setQuestion(dto.getQuestion());
        entity.setCorrectAnswer(dto.getCorrectAnswer());
        entity.setIncorrectAnswers(dto.getIncorrectAnswers());
        return entity;
    }


    // TODO: Find a simpler way to convert from one table to another if the data is identical so this nonsense isn't necessary
    public List<QuestionCollectingEntity> questionListDTOtoCollectingEntity(List<QuestionDTO> dtos) {
        List<QuestionCollectingEntity> entities = new ArrayList<>();
        for (int i = 0; i < dtos.size(); i++) {
            QuestionDTO dto = dtos.get(i);
            entities.add(questionDTOtoCollectingEntity(dto));
        }
        return entities;
    }
   
    public QuestionCollectingEntity questionDTOtoCollectingEntity(QuestionDTO dto) {
        QuestionCollectingEntity entity = new QuestionCollectingEntity();
        Category category = categoryRepository.findByName(decodeString(dto.getCategory())).orElseThrow(() -> new IllegalStateException("Category not in DB!"));
        entity.setCategory(category);
        entity.setDifficulty(dto.getDifficulty());
        entity.setType(dto.getType());
        entity.setQuestion(dto.getQuestion());
        entity.setCorrectAnswer(dto.getCorrectAnswer());
        entity.setIncorrectAnswers(dto.getIncorrectAnswers());
        return entity;
    }


    /** 
     * For some reason, the categories come with special characters,
     * But the categories from the questions dont
     * So this has to be manually fixed if they are to be stored in the db (otherwise namematching doesnt work)
     */
    private String decodeString(String text){
        return StringEscapeUtils.unescapeHtml4(text);
    }
}
