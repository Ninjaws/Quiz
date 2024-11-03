package com.ninjaws.quiz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ninjaws.quiz.entities.QuestionCollectingEntity;

/**
 * This table is used for collecting questions from the external API
 * When it finishes, the data from this repository will replace the data in the QuestionRepository, to ensure it stays fresh
 * This should be scheduled once every day
 */
public interface QuestionCollectingRepository extends JpaRepository<QuestionCollectingEntity, Long>{
    
}
