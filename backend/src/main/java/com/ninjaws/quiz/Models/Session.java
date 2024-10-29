package com.ninjaws.quiz.Models;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Session {
    private final String id;
    private List<Question> questions;

    public Session() {
        this.id = UUID.randomUUID().toString();
    }
}