package com.ninjaws.quiz.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ninjaws.quiz.models.QuizSettings;
import com.ninjaws.quiz.models.Request;

public class QueueServiceTest {

    private QueueService queueService;

    @BeforeEach
    public void setUp() {
        queueService = new QueueService();
    }

    @Test
    public void testAddToQueue() {
        QuizSettings quizSettings = new QuizSettings();
        Request request = new Request("session1", quizSettings);

        boolean added = queueService.addToQueue(request);
        assertTrue(added);

        for (int i = 0; i < 4; i++) {
            added = queueService.addToQueue(new Request("session" + (i + 2), new QuizSettings()));
            assertTrue(added);
        }

        added = queueService.addToQueue(new Request("session6", new QuizSettings()));
        assertFalse(added);
    }

    @Test
    public void testPopFromQueue() {
        QuizSettings quizSettings1 = new QuizSettings();
        QuizSettings quizSettings2 = new QuizSettings();

        Request request1 = new Request("session1", quizSettings1);
        Request request2 = new Request("session2", quizSettings2);

        queueService.addToQueue(request1);
        queueService.addToQueue(request2);

        Request poppedRequest = queueService.popFromQueue();
        assertEquals(request1, poppedRequest);

        poppedRequest = queueService.popFromQueue();
        assertEquals(request2, poppedRequest);

        assertNull(queueService.popFromQueue());
    }

    @Test
    public void testPopFromEmptyQueue() {
        assertNull(queueService.popFromQueue());
    }
}