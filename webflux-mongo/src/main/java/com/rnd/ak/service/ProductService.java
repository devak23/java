package com.rnd.ak.service;

import com.rnd.ak.dto.ProductDTO;
import com.rnd.ak.repo.ProductRepository;
import com.rnd.ak.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Flux<ProductDTO> getAllProducts() {
        return productRepository.findAll().map(AppUtils::entityToDTO);
    }

    public Mono<ProductDTO> getProduct(String id) {
        return productRepository.findById(id).map(AppUtils::entityToDTO);
    }

    public Flux<ProductDTO> getProductsInRange(double min, double max) {
        return productRepository.findByPriceBetween(Range.closed(min, max));
    }

    public Mono<ProductDTO> saveProduct(Mono<ProductDTO> productDTOMono) {
        return productDTOMono
                .map(AppUtils::dtoToEntity)
                .flatMap(productRepository::insert)
                .map(AppUtils::entityToDTO);
    }

    public Mono<ProductDTO> updateProduct(Mono<ProductDTO> productDTOMono, String id) {
        return productRepository.findById(id)
                .flatMap(p -> productDTOMono.map(AppUtils::dtoToEntity))
                .doOnNext(e -> e.setId(id))
                .flatMap(productRepository::save)
                .map(AppUtils::entityToDTO);
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }
}
