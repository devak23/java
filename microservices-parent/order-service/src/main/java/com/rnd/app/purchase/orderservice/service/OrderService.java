package com.rnd.app.purchase.orderservice.service;

import com.rnd.app.purchase.orderservice.dto.OrderLineItemsDto;
import com.rnd.app.purchase.orderservice.dto.OrderRequest;
import com.rnd.app.purchase.orderservice.model.Order;
import com.rnd.app.purchase.orderservice.model.OrderLineItems;
import com.rnd.app.purchase.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> lineItems = orderRequest.getOrderLineItems()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setLineItems(lineItems);
        orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto lineItemDto) {
        OrderLineItems lineItem = new OrderLineItems();
        lineItem.setPrice(lineItemDto.getPrice());
        lineItem.setQuantity(lineItemDto.getQuantity());
        lineItem.setSkuCode(lineItemDto.getSkuCode());

        return lineItem;
    }
}
