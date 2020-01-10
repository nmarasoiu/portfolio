# portfolio
To run, please execute this command line:
bash equity.sh <filepath>

Since the requirement was for it to be simple (not over engineered), I applied this to the extreme that, in some respects I consider it under engineered:
Normally I would also do a spring context with interfaces and combining those separate aspects of http gateway, file format parsing, and portfolio summarization, in separate interfaces + implementations, wired in an IoC context.
Also the URI would be a configuration.
So if you would like for me to apply these typical things, I would definitely like it.
But for now I left it as simple as possible.
The request is done synchronously, without explicit timeouts or retries.
If there would be more cryptos trading, a concurrent number of requests would go maybe over HTTP/2 or just connection pool,
Let's say he would have 50 cryptos (unrealistic, only 20 tradeable).
Then I would make 5 configurable concurrent async even nonblocking requests.
Upon completing each request, a new one is made on that connection.
Until all 50 are responded, or timed out and retries exhausted.
For this, Reactor Flux or similar Observable is good.
Also there are non blocking http clients - the SDK one offers async, not sure if non blocking too.
Anyways, this would be applicable for thousands at least of lines.
Thank you,
Nicu
