package com.ak.reactive.utils.lectures.section02;

import com.ak.reactive.utils.Util;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicReference;

/**
 * In all the cases so far we have been dealing with the "subscribe()" method which asks us to provide an
 * implementation of what will you do if you A) Get the item, B) Get an error, C) Upon completion. The mechanism of
 * 'streaming' was abstracted away from us. In this example, we will see what to do if we are not going with the default
 * subscription provided by the reactive framework.
 */
public class Lec06Subscription {

    public static void main(String[] args) {
        AtomicReference<Subscription> subsRef = new AtomicReference<>();

        // Here we will invoke a subscribeWith API and then provide our own Subscription model. For simplicity and
        // understanding, our model is not going to do anything. However, in complicated cases, one may need to provide
        // their own subscription model.

        Flux.range(1, 20)

                // The subscribeWith will require an implementation of following methods
                .subscribeWith(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        System.out.println("Received subscription: " + subscription);
                        // if you run this till the line above, you will see it has not done anything. When there are
                        // 20 numbers, we would expect the numbers to be emitted by the publisher/producer. But nothing
                        // happened when we executed the program. The reason for this behavior is because the subscription
                        // did not request anything from the publisher. In order to request for data, we will have to
                        // invoke the request() method asking the publisher to emit data. In order to do so, we will
                        // store the subscription object into a reference.
                        subsRef.set(subscription);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("Item received: " + integer);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("Error occurred: " + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Finished listening to stream");
                    }
                });

        // In order to ask publisher to emit data, we are now invoking the `request()` on the subsRef handle we stored
        // earlier. Here we are adding a bit of delay to make us understand the output.
        Util.sleep(2);
        System.out.println("Requesting for 3 items...");
        subsRef.get().request(3);
        Util.sleep(2);
        System.out.println("Requesting for 2 items...");
        subsRef.get().request(2);
        Util.sleep(2);
        System.out.println("Now cancelling the subscription...");
        subsRef.get().cancel();
        Util.sleep(2);
        System.out.println("After canceling, requesting for 3 items again...");
        subsRef.get().request(4);
        Util.sleep(2);
        System.out.println("Done");

        // Notice that `onSubscribe` got called only once where as the `onNext()` gets called everytime we make a
        // request to the publisher. So why do we have to do this i.e. `subsRef.get().request(3)` now and not before?
        // Because we are providing our own model for subscription. by invoking `subscribeWith`. The default method
        // `subscribe()` handles all these things for us.

        // Also note that after calling the `cancel()` on subscription, requesting for more data on the subscription
        // object does NOT get you more data.
    }
}
