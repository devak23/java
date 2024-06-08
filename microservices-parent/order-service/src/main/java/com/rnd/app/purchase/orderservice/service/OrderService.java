package com.rnd.app.purchase.orderservice.service;

import com.rnd.app.purchase.dto.InventoryResponse;
import com.rnd.app.purchase.orderservice.dto.OrderDto;
import com.rnd.app.purchase.orderservice.dto.OrderLineItemsDto;
import com.rnd.app.purchase.orderservice.dto.OrderRequest;
import com.rnd.app.purchase.orderservice.model.Order;
import com.rnd.app.purchase.orderservice.model.OrderLineItems;
import com.rnd.app.purchase.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;


    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> lineItems = orderRequest.getOrderLineItems()
                .stream()
                .map(this::mapToModel)
                .toList();
        order.setLineItems(lineItems);

        List<String> skuCodes = order.getLineItems().stream().map(OrderLineItems::getSkuCode).toList();
        // call inventory-service to check if the product is in the inventory
        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8082/api/v1/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();


        assert inventoryResponses != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock. Please try again later!");
        }
    }

    private OrderLineItems mapToModel(OrderLineItemsDto lineItemDto) {
        OrderLineItems lineItem = new OrderLineItems();
        lineItem.setPrice(lineItemDto.getPrice());
        lineItem.setQuantity(lineItemDto.getQuantity());
        lineItem.setSkuCode(lineItemDto.getSkuCode());

        return lineItem;
    }

    public List<OrderDto> getOrders() {
        return orderRepository.findAll().stream().map(this::mapToDto).toList();
    }

    private OrderDto mapToDto(Order o) {
        return OrderDto.builder()
                .id(o.getId())
                .orderNumber(o.getOrderNumber())
                .lineItems(o.getLineItems().stream().map(this::mapLineItems).toList())
                .build();
    }

    private OrderLineItemsDto mapLineItems(OrderLineItems ol) {
        return OrderLineItemsDto.builder()
                .id(ol.getId())
                .skuCode(ol.getSkuCode())
                .price(ol.getPrice())
                .quantity(ol.getQuantity())
                .build();
    }
}
