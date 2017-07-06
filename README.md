# Java Programs
Just a collection of java programs for a quick mental note.
Consists of:
1. Design Patterns
- Adapter, Builder, Command, NullObject, Observer, Singleton, Strategy, Visitor
2. Custom ClassLoader
3. Concurrency
- Producer consumer implementation with PriorityBlockingQueue
- ReadWriteLock implementation using synchronized keyword
- BoundedBuffer (an implementation of ArrayBlockingQueue)
- ReentrantReadWriteLock (a simple implementation)
- Futures and Callables (a simple word search example)
- Synchronization gotcha (Effective Java)
- Futures using ExecutorService
- Added a sample program that uses SynchronousQueue for Producer Consumer problem
- Added a sample implementatin of ThreadPool. It has a problem that pool.stop doesn't work properly.
- Added examples of BlockingServer, ThreadedBlockingServer, ThreadPoolBlockingServer, NIOBlockingServer, PollingNIOBlockingServer
- Added a core package with a sample program that tells you how many threads are allowed by the OS on your machine
- Added a sample application which processes 2 stocks GOOG and APPL
- Added a simple race condition example
4. NIO examples
- WordCount
- Direct and NonDirect ByteBuffer file read
- MemoryMappedFile example
5. Core examples
- OutOfMemory analysis
- Added a simple DBConnectionPool impementation
- Added a program to find the first non repeating character in a string
- Added a sample program for testing String Deduplication (VM argument)
- Added a vmoptions list from my machine (linux antergos - thinkpad X220 core i5)
6. Documentation
- Added a cheatsheet for GC options
- Added a cheatsheet for OOM causes and solutions
- Added notes on Garbage Collection
