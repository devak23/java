package com.ak.reactive.lectures.section04.helper;

import com.ak.reactive.utils.Util;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrder {
    private String item;
    private String price;
    private int customerId;

    public PurchaseOrder(int customerId) {
        this.customerId = customerId;
        this.item = Util.faker().commerce().productName();
        this.price = Util.faker().commerce().price();
    }
}
