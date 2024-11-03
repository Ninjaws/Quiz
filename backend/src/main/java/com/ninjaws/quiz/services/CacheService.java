package com.ninjaws.quiz.services;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.ninjaws.quiz.models.Session;

@Service
public class CacheService {

    /**
     * Stores all active sessions
     * Gets added to when a user requests data
     * Gets removed from when a user requests their score (by sending their answers)
     */
    private final Cache<String,Session> sessions;

    public CacheService() {
        sessions = (Caffeine.newBuilder()
        .expireAfterWrite(1,TimeUnit.HOURS)
        .build());
    }

    public void addToCache(String key, Session session) {
        sessions.put(key, session);
    }

    public Session getFromCache(String sessionId) {
        return sessions.getIfPresent(sessionId);
    }

    public void removeFromCache(String sessionId) {
        sessions.invalidate(sessionId);
    }
}
