package com.ninjaws.quiz.models;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Session {
    private final String id;
    private int statusCode = 0;
    private List<Question> questions;

    public Session() {
        this.id = UUID.randomUUID().toString();
    }
}