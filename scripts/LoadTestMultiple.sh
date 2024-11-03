#!/bin/bash

start_time=$(date +%s)

for i in {1..1000}; do
  ./LoadTestOne-Silent.sh &
#   sleep 0.1
done

wait  # Wait for all background jobs to finish

end_time=$(date +%s)  # Capture the end time

# Calculate and print the elapsed time
elapsed_time=$((end_time - start_time))
echo "Done!"
echo "Total time taken: $elapsed_time seconds"
