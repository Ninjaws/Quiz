package com.ninjaws.quiz.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;


/**
 * The response sent by the external API
 */
@Getter
@Setter
public class ApiResponse {
    @JsonProperty("response_code")
    private int responseCode;
    @JsonProperty("results")
    private List<Question> results;
}
