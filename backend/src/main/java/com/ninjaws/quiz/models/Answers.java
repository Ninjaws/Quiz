package com.ninjaws.quiz.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Answers {
    private String sessionId;
    private List<String> answers;

    public Answers() {}

    public Answers(String sessionId) {
        this.sessionId = sessionId;
    }
}
