package com.rnd.ak.controller;

import com.rnd.ak.dto.ProductDTO;
import com.rnd.ak.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Flux<ProductDTO> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Mono<ProductDTO> getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }

    @GetMapping("/range")
    public Flux<ProductDTO> getProductsInRange(@RequestParam("min") double min, @RequestParam("max") double max) {
        return productService.getProductsInRange(min, max);
    }

    @PostMapping
    public Mono<ProductDTO> saveProduct(@RequestBody Mono<ProductDTO> productDTO) {
        return productService.saveProduct(productDTO);
    }

    @PutMapping("/{id}")
    public Mono<ProductDTO> updateProduct(@RequestBody Mono<ProductDTO> productDTO, @PathVariable String id) {
        return productService.updateProduct(productDTO, id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }
}
