package com.ninjaws.quiz.components;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Sets up the database, and adds the categories
 * 
 * Fills up the primary database
 * 
 * Then, at night, creates a new database, adds categories, and then starts filling it up
 * When full, swaps the databases, and wipes the old one
 */
public class StartupRunner  implements CommandLineRunner {
    private final JdbcTemplate primaryJdbcTemplate;

    public StartupRunner(JdbcTemplate primaryJdbcTemplate) {
        this.primaryJdbcTemplate = primaryJdbcTemplate;
    }

    @Override
    public void run(String... args) {
        // Initialization logic here
        primaryJdbcTemplate.execute("CREATE TABLE IF NOT EXISTS test_table (id INT PRIMARY KEY, name VARCHAR(255))");
        primaryJdbcTemplate.update("INSERT INTO test_table (id, name) VALUES (?, ?)", 1, "Sample Data");
    }
}
