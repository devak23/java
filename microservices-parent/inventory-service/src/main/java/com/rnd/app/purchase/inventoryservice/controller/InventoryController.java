package com.rnd.app.purchase.inventoryservice.controller;

import com.rnd.app.purchase.inventoryservice.dto.InventoryDto;
import com.rnd.app.purchase.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping ("/{sku-code}")
    @ResponseStatus (HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode) {
        return inventoryService.isInStock(skuCode);
    }

    @GetMapping ("/all")
    public List<InventoryDto> getAllInventory() {
        return inventoryService.getAllInventory();
    }
}
