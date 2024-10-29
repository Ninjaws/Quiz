package com.ninjaws.quiz.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

/**
 * Holds the question, answers, and correct answer
 * TODO: Should not send correct answer to the user
 */
// @Setter()
@Getter()
public class Question {
    private String type;
    private String difficulty;
    private String category;
    private String question;
    
    // @JsonIgnore
    private String correctAnswer;
    
    // @JsonIgnore
    private List<String> incorrectAnswers;

    @JsonProperty("answers")
    public List<String> getAnswers() {
        List<String> possibleAnswers = new ArrayList<>(incorrectAnswers);
        possibleAnswers.add(correctAnswer);
        Collections.shuffle(possibleAnswers);
        return possibleAnswers;
    }

    @JsonProperty("correct_answer")
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @JsonProperty("incorrect_answers")
    public void setIncorrectAnswer(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    @JsonProperty("correct_answer")
    @JsonIgnore
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @JsonProperty("incorrect_answers")
    @JsonIgnore
    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }
}
