package com.ninjaws.quiz.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizSettings {

    @Min(1)
    @Max(50)
    private int amount = 10;
    private Integer category;
    private String difficulty;
    private String type;

    public QuizSettings(){}

    /** Inverse of toApiQuery */
    public QuizSettings(int amount, Integer category, String difficulty, String type) {    
        this.amount = amount;
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;
        // this.category = category == null ? Category.ANY : Category.values()[category];
        // this.difficulty = difficulty == null ? Difficulty.ANY : Difficulty.valueOf(difficulty.toUpperCase());
        // this.type = type == null ? Type.ANY : type.equals("boolean") ? Type.BOOLEAN : Type.MULTIPLE;
    }

    /**
     * Ensures the value is between 1 and 50
     * @Valid doesn't seem to be enforcing it, so this ensures the value is legal
     */
    public void setAmount(@Valid int amount) {
        this.amount = Math.max(1, Math.min(amount, 50));
    }

    /**
     * Converts the class to a format that can be provided as arguments for the api request
     * @return The resulting request arguments as String
     */
    public String toApiQuery() {
        StringBuilder query = new StringBuilder("?amount=" + amount);
        
        if (category != null) {
            query.append("&category=").append(category);
        }

        if (difficulty != null ) {
            query.append("&difficulty=").append(difficulty);
        }

        if (type != null ) {
            query.append("&type=").append(type);
        }

        return query.toString();
    }
}
