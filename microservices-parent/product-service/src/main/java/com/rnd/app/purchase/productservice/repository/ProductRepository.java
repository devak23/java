package com.rnd.app.purchase.productservice.repository;

import com.rnd.app.purchase.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository  extends MongoRepository<Product, String> {
}
