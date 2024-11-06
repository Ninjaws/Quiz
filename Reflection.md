# Reflection

## Issues during testing (October 30th)
### Scheduler
I don't know if it was the scheduler specifically, but whenever I try to test it, both the cache and the queue seem to become empty after the first function is completed.
I don't know how to solve this yet


## Caching solution (October 30th)
### Lack of data
Not being able to gather blocks of 50 is a big issue. The idea of reusing blocks becomes less viable if I can't just get a big chunk every time for others to use.
I can't force a big chunk, because it might just return a status 1, and then the user gets nothing.

Perhaps I can separate the questions myself and make a collection, that i can build the requests from. However, for every quest tracking if someone has already seen is a huge pain.


## Possible solution (October 31st)
Cache all data, 50 at a time
Store it in a database
When its done, let the db make blocks for quick cache access

Then randomize when someone asks for an item from the cache
Or return a failure if there are not enough items (a la the api itself)

Every night, create a new db in the background and start filling it (should take ~34 minutes for all 20k items the external API has)
When it's full, replace the old database with the new one, and delete the old one.
Then start replacing the cache items one at a time.

Users shouldn't feel this at all, because the active sessions are stored in their own cache items

# Progress so far (November 3rd)
There only seem to be ~4100 items, making the collecting phase only last around 7 minutes.

Swapping dbs is replaced with swapping tables, seems to work just as fine, yet much simpler to set up

TODO: Add a caching state, and start filling up each unique combination with the items from the db
This should lead to performance gains

## Possible future alternative
In the current state (when the cache is done), the backend might hold data 3 times (Cache, active DB table, and background DB table during the night)

### Purely Cached solution
- Every night
- Check for each category+difficulty combination how many questions there are (API has an endpoint for this)
- Track which one you are on, and how many you have
- Take blocks of 50, until the remaining items are less than 50, then get that exact amount
- When they are gathered, either 
    1. Divide them between 3 cache items: cat:dif:any/multiple/boolean 
    - (Manually filter on the type, since the API sends questions with either option together), or 
    2. In one like cat:dif:any and then filter it by hand when a user asks for it
    - (This will replace the collection put there the night before)
- Then move to the next one
- Stop when the last combination has been collected

#### When a user requests data: 
- Get the items from the cache
- Less items than requested: Inform the user to try a different combination
- More items than requested: Randomize and select a subset

### Tradeoffs
- Advantage: Less total data held on the server

- Downside: Slower gathering, since there are 25+3+4=300 unique combinations (each of which will end with a request of less than 50 items), it will require a decent number requests more than the bulk collecting that the DB does.