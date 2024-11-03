package com.ninjaws.quiz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninjaws.quiz.entities.Category;
import com.ninjaws.quiz.entities.QuestionCollectingEntity;
import com.ninjaws.quiz.entities.QuestionEntity;
import com.ninjaws.quiz.models.QuizSettings;
import com.ninjaws.quiz.repositories.CategoryRepository;
import com.ninjaws.quiz.repositories.QuestionCollectingRepository;
import com.ninjaws.quiz.repositories.QuestionRepository;

import jakarta.transaction.Transactional;

@Service
public class DataService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionCollectingRepository collectingRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<QuestionEntity> getAllQuestions() {
        return questionRepository.findAll();
    }

    public QuestionEntity saveQuestion(QuestionEntity question) {
        return questionRepository.save(question);
    }

    public List<QuestionEntity> saveQuestions(List<QuestionEntity> questions) {
        return questionRepository.saveAll(questions);
    }

    /**
     * Tries to get questions that match the specified QuizSettings
     *
     * @return The questions if the query was possible, and an empty array if not
     */
    public List<QuestionEntity> lookupQuestions(QuizSettings quizSettings) {
        return questionRepository.lookupQuestions(quizSettings.getCategory(), quizSettings.getDifficulty(), quizSettings.getType(), quizSettings.getAmount());
    }

    public List<Category> saveCategories(List<Category> categories) {
        return categoryRepository.saveAll(categories);
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }
    /**
     * Moving the collected questions to the questions list
     *
     */
    @Transactional
    public void migrateQuestions() {
        questionRepository.deleteAll();
        questionRepository.moveCollection();
        collectingRepository.deleteAll();
    }

    public List<QuestionCollectingEntity> collectQuestions(List<QuestionCollectingEntity> questions) {
        return collectingRepository.saveAll(questions);
    }
}
