package com.ak.reactive.lectures.section4;

import com.ak.reactive.lectures.section4.helper.CustomerService;
import com.ak.reactive.lectures.section4.helper.OrderService;
import com.ak.reactive.utils.Util;

/**
 * So the idea here is  - say we have a Flux of Customers and for every customer we require to fetch the orders. So, how
 * will you do this? We will first create a flux of Customers and then use a map to find the orders for a given customer?
 * That won't work! Why? Because map is a one-to-one operator meaning, it iterates over the collection and processes
 * each one to produce an output that corresponds to the input. In our case, if the order service returns a flux of Orders
 * then the map operator will send the flux of orders within its own flux as it will treat the entire flux <orders> as
 * one entity to be returned for 1 item of the customer.
 *
 * So what do we do? We need a flatmap here. The flatmap operator will convert the flux<orders> into items that will be
 * pushed down the current flux.
 */
public class Lec12FlatMap {

    // 1. First create a Customer.
    // 2. Iterate over flux using flatmap
    // 3. Subscribe to the resulting data elements

    public static void main(String[] args) {
        // This shows what happens when you use a map instead of a flatmap. The map wont "unpack" the elements in the flux
        // It rather returns the Flux itself.
        CustomerService.getCustomers()
                .map(customer -> OrderService.getOrdersFor(customer.getCustomerId()))
                .subscribe(Util.getSubscriber());

        // The flatmap will get rid of the extra abstraction of Flux over the elements and will return them as we want.
        CustomerService.getCustomers()
                .flatMap(customer -> OrderService.getOrdersFor(customer.getCustomerId()))
                .subscribe(Util.getSubscriber());

        Util.sleep(10);
    }
}
