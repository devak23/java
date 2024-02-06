package com.ak.reactive.lectures.section4;

import com.ak.reactive.lectures.section4.helper.CustomerService;
import com.ak.reactive.lectures.section4.helper.OrderService;
import com.ak.reactive.utils.Util;

/**
 * What if we had a need that Orders from customer 1 needs to be drained before the Orders from customer2 are picked up
 * for transmission? A flatmap as we saw dumps everything into the resultant flux but doesn't maintain the order.
 * Enter concatMap.
 */
public class Lec13ConcatMap {

    public static void main(String[] args) {

        CustomerService.getCustomers()
                .concatMap(customer -> OrderService.getOrdersFor(customer.getCustomerId()))
                .subscribe(Util.getSubscriber());

        Util.sleep(10);
    }
}
