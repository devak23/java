package com.rnd.ak.repo;

import com.rnd.ak.dto.ProductDTO;
import com.rnd.ak.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Flux<ProductDTO> findByPriceBetween(Range<Double> priceRange);
}
