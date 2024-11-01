#!/bin/bash

# Step 1: Get the sessionId from the questions endpoint
sessionIdJson=$(curl -s http://localhost:9090/quiz/questions?amount=5)

statusCode=$(echo "$sessionIdJson" | grep -o '"statusCode":[0-9]*' | sed 's/"statusCode"://')

if [ "$statusCode" -ne 0 ]; then
    echo "Exiting: statusCode is not 0."
    exit 1
fi

sessionId=$(echo "$sessionIdJson" | grep -o '"sessionId":"[^"]*"' | sed 's/"sessionId":"//;s/"//')

if [ -z "$sessionId" ]; then
    echo "Failed to get sessionId"
    exit 1
fi

echo "Received sessionId: $sessionId"

# Step 2: Poll the status endpoint every 2 seconds
while true; do
    status_response=$(curl -s http://localhost:9090/quiz/status/"$sessionId")

    if [ $? -eq 0 ]; then
        # Check if the response is JSON (starts with '{')
        if [[ $status_response == \{* ]]; then
            echo "$sessionId: done!"
            break
        fi
    else
        echo "Status check failed, retrying..."
    fi

    sleep 2
done
