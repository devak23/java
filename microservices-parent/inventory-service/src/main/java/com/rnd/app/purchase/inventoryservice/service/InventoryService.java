package com.rnd.app.purchase.inventoryservice.service;

import com.rnd.app.purchase.inventoryservice.dto.InventoryDto;
import com.rnd.app.purchase.inventoryservice.model.Inventory;
import com.rnd.app.purchase.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional (readOnly = true)
    public boolean isInStock(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    public List<InventoryDto> getAllInventory() {
        return inventoryRepository.findAll().stream().map(this::mapToDto).toList();
    }

    private InventoryDto mapToDto(Inventory inventory) {
        return InventoryDto.builder()
                .id(inventory.getId())
                .skuCode(inventory.getSkuCode())
                .quantity(inventory.getQuantity())
                .build();
    }
}
