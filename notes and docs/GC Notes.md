#GARBAGE COLLECTION#

Trade offs are inevitable. When it comes to GC, we need to play with only 3 variables:

<span style="color:brown">**Throughput**</span>: The amount of time application does meaningful work rather than spending time in GC cycles. The target throughput is generally 99% with 1% time spent in GC cycles. ( -XX:GCTimeRatio=99)
 
<span style="color:brown">**Latency**</span>: The time taken by the application to respond to events (which are impacted by "GC pauses"). The GC pauses can be controlled via -XX:MaxGCPauseMillis=200 (this is default in G1)

<span style="color:brown">**Memory**</span>: The amount of memory allocated which stores the state and is often copied or moved around during its management (survivor compaction). This parameter is configured via the -Xms1024m and -Xmx1024m flags. The set of active objects retained by the application at any given point is called the "Live Set"


These 3 variables generally are interdependent and cannot be controlled singularly. For example:

1. To a large extent the amortized cost of GC can be reduced by providing GC algorithms with more memory
2. The observed worst-case latency-inducing pauses can be reduced by controlling the live set and keeping the heap size (memory) small
3. The "GC pause frequency" can be reduced by managing the heap and generation sizes and controlling application's object allocation rate
4. The large "GC pause frequency" can be reduced by concurrently running GC with application at the expense of throughput


For some systems like batch processing, latency may not be as important as throughput. However for other systems that incorporate a human interaction, the latency factor becomes more critical than the throughput. On the other hand, we also have cases where a system is bounded by memory and therefore latency and throughput may not even be considered.

<span style="color:blue">**Why Generational Garbage Collectors?**</span>
The concept of GC started with the idea that: objects, newly created by the application are likely to die sooner and very few of them live very long. This is called as "infant mortality" or "weak generational hypothesis". For ex: objects created in a for loop will die soon whereas static strings will remain for the lifetime of the application. Thus, by separation of the objects based on their age (generation), we know that the newly created collection of objects will be sparse of live objects. Therefore a GC algorithm that looks for these few live objects and moves them to a different region will theoretically be more efficient than any other GC algorithm. Thus arose a need to partition the heap into Eden space (young generation) and Tenured space (old generation) and hence came bout the development of "Generational garbage collector". Based on the weak generational hypothesis, the heap is divided into

1. **Young Generation**: This is where the newly created objects will be placed after object allocation. Minor GC's occur frequently here and are very efficient as these areas are smaller in size and likely to contain more garbage.
2. **Old Generation**: This is where the objects from the young generation are promoted into. This area is larger than the young generation and its occupancy grows more slowly. the GC occurs less frequently here and is called the major GC. However when they do occur, they are fairly lengthy.

The age of the object is determined by how many minor and major GC cycles it survived.

<span style="color:blue">**How are the objects allocated on the heap?**</span>
The object allocation is done using "bump-the-pointer" technique. As per this technique, the end address of the last object allocated is tracked (called the top). When a new object requirement is satisified, the top is bumped up to the end of newly allocated object. In a multi-threaded system, this becomes tricky as multiple threads will compete for locations in Eden to allocate object. If "synchronization approach" were to be used to allocate the object in Eden, the performance would plummet. Therefore, the Hotspot VM adopted a technique called TLAB.
Each thread is allocated a Thread Local Allocation Buffer (TLAB) which is a part of heap. Since only one thread allocates the object in the TLAB, the need for synchronization is greatly reduced thereby improving the object allocation rate. Assuming that a TLAB can hold 10 objects, it needs to synchronize only when it creates the 11th object on the heap. If a thread runs out of TLAB (relatively infrequent) it needs to request space from Eden in a multi-threaded way.
Some objects can be huge and may not fit in a TLAB. These objects can be controlled by the VM parameter -XX:PretenureSizeThreshold=<size> which denotes "max object size allowed to be allocated in young generation". If the object is humongos (size larger than Eden) then the object may be created directly into the Tenured generation!
In short, the allocation algorithm is something as follows:

    if (tlab_top + size (object) < tlab_end) {
        // allocate space i.e. bump up the pointer
        tlab_top = tlab_top + size (object);
    } else if (tlab_top == tlab_end) { // if tlab is full.
        // request more space from Eden
    } else if (tlab_end < tlab_top + size (object)  < eden_end ) {
        // allocate object into eden i.e. bump up the eden pointer
        synchronized (eden) {
            eden_top = eden_top + size (object) 
        }
    } else if (eden_end < eden_top + size (object)) {
        // perform minor_gc and try again. if it fails, go to else block
    } else {
        // create the object in the tenured region
        tenured_top = tenured_top + size (object)
    }


The size of TLAB can be set with the VM flag -XX:TLABSize. The individual object creation is very cheap (in fact cheaper than malloc in C). However, the rate of object allocation is directly proportional to "minor GC" frequency.

<span style="color:blue">**How does the Garbage Collector track objects?**</span>
In order to keep minor GC quick, GC algorithm has to identify the live objects quickly without scanning any region (and definitely not the old generation). Therefore, GC uses a data structure called as "card table" which is nothing but an array of bytes. Each region in the heap is further divided into chunks of 512 bytes called cards. For every card, one byte is registered in the card table. When a reference to an object is updated, the particular card is marked dirty and the corresponding value in the card table is also updated. Thus, during minor collection, only the dirty cards are scanned for a potential old-to-young reference and is known as - Remembered Set (RS). The RS also becomes the part of the "roots" for the GC.

The 'card marking' uses a bit (or a byte) to indicate a word or a region in the old generation that is suspected of using the reference. These marks may be precise or imprecise i.e. it could record the exact location or just a region in memory.

Now, live objects are any objects that can be traced into the object graph. Thus, the process for hunting the live objects starts from the roots which includes thread stacks, static variables, special references from JNI code and other areas where live objects are likely to be found. The reference to an object can be prevented from being garbage collected if it chains from the GC roots. The GC "paints" any object as live if it can reach the object. The overall time for marking is proportional to the amount of live objects and references regardless of their size. The marking happens in the S-T-W (stop the world) pause state where all mutators are stopped.

However, the mutator threads cannot be stopped arbitrarily; therefore GC ensures that it is at a "Global Safepoint" before it begins marking the objects. A "Global Safepoint" is reached when all the threads are at a "GC safe point". A "GC safe point" is a point or a range in thread's execution where the collector can identify all the references in the thread's execution stack.

In concurrent marking, all the reachable objects are marked as live, but the object graph keeps changing as the marker works. This can lead to a classic race condition where the application can move a reference that's not seen by the collector (dead reference) into an object that has already been visited (live reference). If this were allowed to happen, then the heap would be corrupted as a valid reference would be garbage collected.

To prevent this from happening, the hotspot VM along with the byte code interpreter and the JIT compiler uses a "write barrier" to maintain the card table. The write barrier is a small piece of code that acts like a listener and updates the card table whenever there is a change in object references that might otherwise be missed by the marker. This operation does hurt the performance a little bit but the presence of the write barrier allows for a faster minor collections thereby improving the end-to-end throughput of the application.