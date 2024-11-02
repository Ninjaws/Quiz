package com.ninjaws.quiz.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {
    @Id
    private Integer id;
    private String name;

    public Category() {}

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
}
