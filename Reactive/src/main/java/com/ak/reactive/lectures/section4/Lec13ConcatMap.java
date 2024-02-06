package com.ak.reactive.lectures.section4;

import com.ak.reactive.lectures.section4.helper.CustomerService;
import com.ak.reactive.lectures.section4.helper.OrderService;
import com.ak.reactive.utils.Util;

public class Lec13ConcatMap {

    public static void main(String[] args) {

        CustomerService.getCustomers()
                .concatMap(customer -> OrderService.getOrdersFor(customer.getCustomerId()))
                .subscribe(Util.getSubscriber());

        Util.sleep(10);
    }
}
