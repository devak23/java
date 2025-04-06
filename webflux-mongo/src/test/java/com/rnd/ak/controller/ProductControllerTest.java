package com.rnd.ak.controller;

import com.rnd.ak.ProductFixture;
import com.rnd.ak.dto.ProductDTO;
import com.rnd.ak.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private WebTestClient webClient;
    @MockBean
    private ProductService productService;

    @Test
    public void givenProduct_whenAddProduct_thenAddsProduct() {
        Mono<ProductDTO> productDTOMono = Mono.just(ProductFixture.getMobile());
        Mockito.when(productService.saveProduct(productDTOMono)).thenReturn(productDTOMono);

        webClient.post().uri("/products")
                .body(Mono.just(productDTOMono), ProductDTO.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void givenSavedProducts_whenGetProducts_thenGetsAllProducts() {
        ProductDTO mobile = ProductFixture.getMobile();
        ProductDTO computer = ProductFixture.getComputer();
        ProductDTO tv = ProductFixture.getTV();

        Flux<ProductDTO> productDTOFlux = Flux.fromIterable(List.of(mobile, computer, tv));
        Mockito.when(productService.getAllProducts()).thenReturn(productDTOFlux);
        Flux<ProductDTO> responseBody = webClient.get().uri("/products")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDTO.class)
                .getResponseBody();
        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(mobile)
                .expectNext(computer)
                .expectNext(tv)
                .verifyComplete();
    }

    @Test
    public void givenAProduct_whenGetProductIsInvoked_thenGetsProduct() {
        ProductDTO mobile = ProductFixture.getMobile();

        Mockito.when(productService.getProduct(ArgumentMatchers.any())).thenReturn(Mono.just(mobile));

        Flux<ProductDTO> responseBody = webClient.get().uri("/products/" + mobile.getId())
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDTO.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p -> p.getName().equalsIgnoreCase("mobile"))
                .verifyComplete();
    }

    @Test
    public void givenAProduct_whenDeleteProduct_thenDeletesProduct() {
        ProductDTO mobile = ProductFixture.getMobile();
        Mockito.when(productService.deleteProduct(ArgumentMatchers.any())).thenReturn(Mono.empty());

        webClient.delete().uri("/products/" + mobile.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void givenAProduct_whenUpdateProduct_thenUpdatesProduct() {
        ProductDTO mobile = ProductFixture.getMobile();
        Mono<ProductDTO> mobileMono = Mono.just(mobile);

        Mockito.when(productService.updateProduct(mobileMono, mobile.getId())).thenReturn(mobileMono);

        webClient.put().uri("/products/" + mobile.getId())
                .body(Mono.just(mobileMono), ProductDTO.class)
                .exchange()
                .expectStatus().isOk();
    }
}
