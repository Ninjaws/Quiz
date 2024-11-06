#!/bin/bash
# Step 1: Get the sessionId from the questions endpoint
sessionIdJson=$(curl -s "http://localhost:9090/quiz/questions?amount=10&category=23&difficulty=medium&type=multiple")

# Extract the status code
statusCode=$(echo "$sessionIdJson" | grep -o '"statusCode":[0-9]*' | sed 's/"statusCode"://')

if [ "$statusCode" -ne 0 ]; then
    exit 1
fi

# Extract the session ID
sessionId=$(echo "$sessionIdJson" | grep -o '"sessionId":"[^"]*"' | sed 's/"sessionId":"//;s/"//')

if [ -z "$sessionId" ]; then
    exit 1
fi

# Step 2: Poll the status endpoint every 2 seconds
while true; do
    status_response=$(curl -s "http://localhost:9090/quiz/status/$sessionId")

    if [ "$status_response" != "null" ]; then
        # Extract the status code from the response
        statusCode=$(echo "$status_response" | grep -o '"statusCode":[0-9]*' | sed 's/"statusCode"://')

        if [ "$statusCode" -ne 0 ]; then
            exit 1
        fi

        # Check if the response is JSON (starts with '{')
        if [[ $status_response == \{* ]]; then
            break
        fi
    fi

    sleep 2
done
