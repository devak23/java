package com.rnd.ak;

import com.rnd.ak.dto.ProductDTO;

public final class ProductFixture {

    public static ProductDTO getMobile() {
        return new ProductDTO("1A", "mobile", 1, 1000);
    }

    public static ProductDTO getComputer() {
        return new ProductDTO("1B", "computer", 1, 2000);
    }

    public static ProductDTO getTV() {
        return new ProductDTO("1C", "TV", 1, 3000);
    }
}
