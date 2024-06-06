package com.rnd.app.purchase.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
//		return args -> {
//			Inventory iPhone = new Inventory();
//			iPhone.setSkuCode("iPhone13");
//			iPhone.setQuantity(100);
//
//			Inventory samsung = new Inventory();
//			samsung.setSkuCode("Samsung S23");
//			samsung.setQuantity(200);
//
//			Inventory motorola = new Inventory();
//			motorola.setSkuCode("Motorola Razr");
//			motorola.setQuantity(0);
//
//			inventoryRepository.save(iPhone);
//			inventoryRepository.save(samsung);
//			inventoryRepository.save(motorola);
//		};
//	}
}
