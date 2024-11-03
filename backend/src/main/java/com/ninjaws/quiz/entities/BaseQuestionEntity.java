package com.ninjaws.quiz.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseQuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    private Category category;

    private String difficulty;
    private String type;
    private String question;
    
    private String correctAnswer;    
    // @ElementCollection // This stores it in a separate table, it's just a set of strings so not required right now
    private List<String> incorrectAnswers;
}
