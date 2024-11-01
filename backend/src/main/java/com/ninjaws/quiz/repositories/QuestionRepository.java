package com.ninjaws.quiz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ninjaws.quiz.models.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
