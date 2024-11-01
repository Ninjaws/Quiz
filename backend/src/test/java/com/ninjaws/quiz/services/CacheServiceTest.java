package com.ninjaws.quiz.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ninjaws.quiz.models.Session;

public class CacheServiceTest {

    private CacheService cacheService;

    @BeforeEach
    public void setUp() {
        cacheService = new CacheService();
    }

    @Test
    public void testAddToCache() {
        String key = "session1";
        Session session = new Session();

        cacheService.addToCache(key, session);

        assertNotNull(cacheService.getFromCache(key));
        assertEquals(session, cacheService.getFromCache(key));
    }

    @Test
    public void testGetFromCache() {
        String key = "session1";
        Session session = new Session();

        cacheService.addToCache(key, session);

        Session retrievedSession = cacheService.getFromCache(key);
        assertNotNull(retrievedSession);
        assertEquals(session, retrievedSession);
    }

    @Test
    public void testRemoveFromCache() {
        String key = "session1";
        Session session = new Session();

        cacheService.addToCache(key, session);
        cacheService.removeFromCache(key);

        assertNull(cacheService.getFromCache(key));
    }

    @Test
    public void testGetFromCacheWithNonExistentKey() {
        assertNull(cacheService.getFromCache("nonExistentKey"));
    }
}
