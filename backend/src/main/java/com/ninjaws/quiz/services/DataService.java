package com.ninjaws.quiz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninjaws.quiz.entities.Category;
import com.ninjaws.quiz.entities.QuestionEntity;
import com.ninjaws.quiz.models.QuizSettings;
import com.ninjaws.quiz.repositories.CategoryRepository;
import com.ninjaws.quiz.repositories.QuestionRepository;

@Service
public class DataService {

    @Autowired
    private QuestionRepository questionRepository;

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

    // public List<Question> getRandomQuestions(String type, String category, String difficulty, int limit) {
    //     return questionRepository.findRandomQuestions(type, limit);
    // }

    public List<QuestionEntity> lookupQuestions(QuizSettings quizSettings) {
        // Long category = Long.valueOf(quizSettings.getCategory().getApiValue() == 1 ? null : quizSettings.getCategory().getApiValue());
        // String difficulty = quizSettings.getDifficulty() == QuizSettings.Difficulty.ANY ? null : quizSettings.getDifficulty().getApiValue();
        // String type = quizSettings.getType() == QuizSettings.Type.ANY ? null : quizSettings.getType().getApiValue();
        // System.out.println(category);
        // System.out.println(difficulty);
        // System.out.println(type);
        
        return questionRepository.lookupQuestions(quizSettings.getCategory(), quizSettings.getDifficulty(), quizSettings.getType(), quizSettings.getAmount());
    }

    /**
     * Tries to get questions that match the specified QuizSettings
     * @return The questions if the query was possible, and an empty array if not
     */
    // public List<Question> getByQuizSettings(QuizSettings quizSettings) {
    //     Specification<Product> spec = Specification.where(QuestionSpecification.hasType(type))
    //     .and(QuestionSpecification.hasCategory(category))
    //     .and(QuestionSpecification.hasDifficulty(difficulty));

    //     return productRepository.findAll(spec);
    // }

    public List<Category> saveCategories(List<Category> categories) {
        return categoryRepository.saveAll(categories);
    }
}
