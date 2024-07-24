package com.modernjava.structuredconcurrency;

import com.modernjava.service.ProductInfoService;
import com.modernjava.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceStructuredConcurrencyTest {

    /*
    ProductInfoService productInfoService = Mockito.spy(ProductInfoService.class);
    ReviewService reviewService = Mockito.spy(ReviewService.class);
     DeliveryService deliveryService = Mockito.spy(DeliveryService.class);

    ProductServiceStructuredConcurrency productServiceStructuredConcurrency
            = new ProductServiceStructuredConcurrency(productInfoService, reviewService, deliveryService);
    */
    @Spy
    ProductInfoService productInfoService;

    @Spy
    ReviewService reviewService;

//    @Spy
//    DeliveryService deliveryService;

    @InjectMocks
    ProductServiceStructuredConcurrency productServiceStructuredConcurrency;

    @Test
    void retrieveProductDetails() {
        var product = productServiceStructuredConcurrency.retrieveProductDetails("ABC");
        assertNotNull(product);
        assertNotNull(product.productInfo());
        assertNotNull(product.reviews());
    }

    @Test
    void retrieveProductDetails_Exception() {
        when(reviewService.retrieveReviews(anyString())).thenThrow(new RuntimeException("Exception when retrieving reviews"));
        var exception = assertThrows(RuntimeException.class, () -> productServiceStructuredConcurrency.retrieveProductDetails("ABC"));

        assertTrue(exception.getMessage().contains("Exception when retrieving reviews"));
    }

}