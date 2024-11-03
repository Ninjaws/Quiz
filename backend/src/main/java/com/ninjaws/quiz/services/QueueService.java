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
    private final BlockingQueue<Request> requests = new LinkedBlockingQueue<>(5);

    /**
     * Offer automatically doesn't add it to the queue if it is full, so no Exceptions thrown
     * @return Whether it's possible to add the request to the queue
     */
    public boolean addToQueue(Request request) {
        return requests.offer(request);
    }

    public Request popFromQueue() {
        return requests.poll();
    }    
}
