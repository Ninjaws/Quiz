package com.ninjaws.quiz.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ninjaws.quiz.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    Optional<Category> findByName(String name);
}
