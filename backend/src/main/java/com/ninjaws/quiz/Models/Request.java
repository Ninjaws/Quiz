package com.ninjaws.quiz.Models;

import lombok.Getter;

@Getter
public class Request {
    private final String sessionId;
    private final String requestUrl;

    public Request(String sessionId, String requestUrl) {
        this.sessionId = sessionId;
        this.requestUrl = requestUrl;
    }
}
