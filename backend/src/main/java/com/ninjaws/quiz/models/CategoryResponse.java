package com.ninjaws.quiz.models;

import java.util.List;

import com.ninjaws.quiz.entities.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    List<Category> trivia_categories;
}
