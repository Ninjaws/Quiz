package com.ninjaws.quiz.services;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Service;

import com.ninjaws.quiz.models.Request;

@Service
public class QueueService {
    
    /**
     * Stores all user requests for data
     * Executes them in order, one every 5 seconds (the limit of the external API)
     * Removes them from the Queue when the request was succesful
     */
    private final BlockingQueue<Request> requests = new LinkedBlockingQueue<>();

    public void addToQueue(Request request) {
        requests.add(request);
    }

    public Request popFromQueue() {
        return requests.poll();
    }    
}
