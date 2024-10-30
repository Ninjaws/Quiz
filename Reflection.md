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
