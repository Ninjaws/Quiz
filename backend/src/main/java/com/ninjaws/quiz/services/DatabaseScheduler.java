package com.ninjaws.quiz.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DatabaseScheduler {
    @Autowired
    private JdbcTemplate secondaryJdbcTemplate;

    @Scheduled(fixedRate = 60000) // Runs every 60 seconds
    public void createAndFillSecondaryDatabase() {
        // Create tables and insert data into the secondary database
        secondaryJdbcTemplate.
        .execute("CREATE TABLE IF NOT EXISTS sample_table (id INT PRIMARY KEY, name VARCHAR(255))");
        secondaryJdbcTemplate.update("INSERT INTO sample_table (id, name) VALUES (?, ?)", 1, "Sample Data");
    }
}
