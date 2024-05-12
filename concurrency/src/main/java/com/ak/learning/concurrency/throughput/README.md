--- 
## Apache JMeter Steps

In our JMeter application, we are going to load a file which contains a list of words and for each word we're going to 
send an HTTP request and wait for the response. The tool will send as many requests as possible and as fast as possible. 
In the end it will give us the throughput of our application simply by taking the number of requests sent divided by the
time it took to get all the responses. 

1. First let's give our test plan a name. 
2. Then we add a thread group which is a group of JMeter threads, and they're going to send HTTP request to our HTTP server.
3. We will dedicate 200 user threads to run concurrently, which should be more than enough to have a decent load on our application.
4. After that, we add a while loop controller which will help us iterate over the input words inside the while loop. 
5. We're going to add a CSV data set configure, which will read the words from a file and feed it into a variable. 
6. The file we re going to load is called search_words.csv. Every line in the file will contain a unique word, which will assign it to a variable called WORD (in caps), which will have a new word ready for us in each iteration.
7. Since each word in the file is on a different line, so we're going to use the return symbol (\n) as the delimiter. 
8. We want to read the file only once so we will switch "the stop thread" on the end of file to true. 
9. Now that we have each word stored in a variable, we can create a condition in the while loop which will continue reading the words from the file as long as we did not reach the end of file symbol. 
10. Now in each iteration, after we read the word from the file and stored in the word variable, we want to send an HTTP request to our HTTP server application asking for the number of times this word appears in the book, so let's add an HTTP request sampler. 
11. The path to our endpoint is "/search" and the query is word we just read from the current iteration. 
12. The protocol is http, and the server is going to be localhost. 
13. Finally, the port we're going to send a request to is 8000 as we defined in the code. 
14. The last thing we need to do is actually get a summary of our performance test. So we will add a listener called "summary report", which will give us the throughput and some other metrics as we run our test. 
15. Just for debugging purposes, we will add the "view results tree" to inspect each individual request to make sure we get a meaningful response from our application and that's it.

Our performance test plan is ready.


---