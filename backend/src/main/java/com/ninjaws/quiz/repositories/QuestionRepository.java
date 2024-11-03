package com.ninjaws.quiz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ninjaws.quiz.entities.QuestionEntity;

import jakarta.transaction.Transactional;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long>{//, JpaSpecificationExecutor<Question> {

    // @Query(value = "SELECT * FROM question AS q " +
    // "WHERE (?1 IS NULL OR q.category = ?1) " + 
    // "AND (?2 IS NULL OR q.difficulty = ?2) " +
    // "AND (?3 IS NULL OR q.type = ?3) " +
    // "ORDER BY RAND() LIMIT ?4", nativeQuery = true)
    @Query(value = "SELECT * FROM question AS q " +
        "WHERE (:categoryId IS NULL OR q.category = :categoryId) " + 
        "AND (:difficulty IS NULL OR q.difficulty = :difficulty) " +
        "AND (:type IS NULL OR q.type = :type) " +
        "ORDER BY RAND() LIMIT :limit;", nativeQuery = true)
    List<QuestionEntity> lookupQuestions(
        @Param("categoryId") Integer categoryId, 
        @Param("difficulty") String difficulty, 
        @Param("type") String type, 
        @Param("limit") int limit);

    /**
     * Moves all data from one table to another
     */
    @Modifying
    @Transactional
    @Query(value="INSERT INTO question SELECT * FROM question_collecting;", nativeQuery=true)
    void moveCollection();
}
