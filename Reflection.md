# Issues

## Issues during testing
### Scheduler
I don't know if it was the scheduler specifically, but whenever I try to test it, both the cache and the queue seem to become empty after the first function is completed.
I don't know how to solve this yet


## Caching solution
### Lack of data
Not being able to gather blocks of 50 is a huge issue. The idea of reusing blocks becomes less viable if I can just get a big chunk every time for others to use
I can't force a big chunk, because it might just return a status 1, and then the user gets nothing.

Perhaps I can separate the questions myself and make a collection, that i can build the requests from. However, for every quest tracking if someone has already seen is a huge pain.


## Possible solution
Cache all data, 50 at a time
Store it in a database
When its done, let the db make blocks for quick cache access

Then randomize when someone asks for an item from the cache
Or return a failure if there are not enough items (a la the api itself)

Every night, create a new db in the background and start filling it (should take ~34 minutes for all 20k items the external API has)
When it's full, replace the old database with the new one, and delete the old one.
Then start replacing the cache items one at a time.

Users shouldn't feel this at all, because the active sessions are stored in their own cache items

# Progress so far
There only seem to be ~4100 items, making the collecting phase only last around 7 minutes.

Swapping dbs is replaced with swapping tables, seems to work just as fine, yet much simpler to set up

TODO: Add a caching state, and start filling up each unique combination with the items from the db
This should lead to performance gains

