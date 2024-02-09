package com.ak.reactive.lectures.section05.assignment;

import com.ak.reactive.utils.Util;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrder {
    private final String category;
    private String item;
    private Double price;
    private int quantity;

    public PurchaseOrder() {
        this.item = Util.faker().commerce().productName();
        this.price = Double.parseDouble(Util.faker().commerce().price());
        this.category = Util.faker().commerce().department();
        this.quantity = Util.faker().random().nextInt(1, 10);
    }
}
