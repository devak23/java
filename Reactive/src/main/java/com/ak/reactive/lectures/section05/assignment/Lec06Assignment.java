package com.ak.reactive.lectures.section05.assignment;

import com.ak.reactive.utils.Util;

public class Lec06Assignment {

    public static void main(String[] args) {

        OrderService orderService = new OrderService();
        InventoryService inventoryService = new InventoryService();
        RevenueService revenueService = new RevenueService();

        // revenue service and inventory service are mere observers of the order service
        orderService.orderStream().subscribe(revenueService.subscribeOrderStream());
        orderService.orderStream().subscribe(inventoryService.subscribeOrderStream());

        inventoryService.inventoryStream().subscribe(Util.getSubscriber("Inventory:"));
        revenueService.revenueStream().subscribe(Util.getSubscriber("Revenue:"));

        Util.sleep(20);
    }
}
