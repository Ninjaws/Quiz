package com.ninjaws.quiz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ninjaws.quiz.entities.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long>{//, JpaSpecificationExecutor<Question> {

    @Query(value = "SELECT * FROM question AS q " +
    "WHERE (?1 IS NULL OR q.category = ?1) " + 
    "AND (?2 IS NULL OR q.difficulty = ?2) " +
    "AND (?3 IS NULL OR q.type = ?3) " +
    "ORDER BY RAND() LIMIT ?4", nativeQuery = true)
    List<QuestionEntity> lookupQuestions(Integer categoryId, String difficulty, String type, int limit);

    // public List<Question> getQuestionsBy(String type, String category, String difficulty, int limit) {
    //     Specification<Product> spec = Specification.where(QuestionSpecification.hasType(type))
    //     .and(QuestionSpecification.hasCategory(category))
    //     .and(QuestionSpecification.hasDifficulty(difficulty));

    //     return productRepository.findAll(spec);
    // }
}
