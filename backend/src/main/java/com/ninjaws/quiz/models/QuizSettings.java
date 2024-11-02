package com.ninjaws.quiz.models;

import com.ninjaws.quiz.models.QuizSettings.Category;
import com.ninjaws.quiz.models.QuizSettings.Difficulty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizSettings {

    public enum Type {
        ANY, BOOLEAN, MULTIPLE;

        public String getApiValue() {
            return this.name().toLowerCase();
        }
    }

    public enum Difficulty {
        ANY, EASY, MEDIUM, HARD;

        public String getApiValue() {
            return this.name().toLowerCase();
        }
    }

    public enum Category {
        ANY, GEN_KNOWLEDGE, ENT_BOOKS, ENT_FILM, ENT_MUSIC, ENT_MUSICAL_THEATRE, ENT_TELEVISION, 
        ENT_VIDEOGAMES, ENT_BOARDGAMES, SCIENCE_NATURE, SCIENCE_COMPUTERS, SCIENCE_MATH, MYTHOLOGY, SPORTS, GEOGRAPHY, HISTORY, POLITICS, ART, CELEBRITIES, 
        ANIMALS, VEHICLES, ENT_COMICS, SCIENCE_GADGETS, ENT_ANIME_MANGA, ENT_CARTOON_ANIMATIONS;
        
        /**
         * For some reason the external API starts with a value of 9 for the General Knowledge category, so we have to adjust
         * ANY also gets adjusted, but it doesn't matter since that one doesn't get used as parameter anyway
         * @return The corrected value
         */
        public int getApiValue() {
            return 8 + this.ordinal();
        }
    }

    @Min(1)
    @Max(50)
    private int amount = 10;

    // private Type type = Type.ANY;

    // private Difficulty difficulty = Difficulty.ANY;

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
    // public String toApiQuery() {
    //     StringBuilder query = new StringBuilder("?amount=" + amount);
        
    //     if (category != null && category != Category.ANY) {
    //         query.append("&category=").append(category.getApiValue());
    //     }

    //     if (difficulty != null && difficulty != Difficulty.ANY) {
    //         query.append("&difficulty=").append(difficulty.getApiValue());
    //     }

    //     if (type != null && type != Type.ANY) {
    //         query.append("&type=").append(type.getApiValue());
    //     }

    //     return query.toString();
    // }

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
