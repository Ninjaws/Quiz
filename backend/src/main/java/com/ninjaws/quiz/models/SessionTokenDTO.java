package com.ninjaws.quiz.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionTokenDTO {
    @JsonProperty("response_code")
    private int responseCode;
    @JsonProperty("token")
    private String token;    
}
