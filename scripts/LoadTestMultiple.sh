#!/bin/bash

start_time=$(date +%s)

for i in {1..6}; do
  ./LoadTestOne-Silent.sh &
#   sleep 0.1
done

wait

end_time=$(date +%s)

elapsed_time=$((end_time - start_time))
echo "Done!"
echo "Total time taken: $elapsed_time seconds"
