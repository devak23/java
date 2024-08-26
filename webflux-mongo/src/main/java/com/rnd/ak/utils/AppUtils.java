package com.rnd.ak.utils;

import com.rnd.ak.dto.ProductDTO;
import com.rnd.ak.entity.Product;
import org.springframework.beans.BeanUtils;

public final class AppUtils {

    public static ProductDTO entityToDTO(Product product) {
        ProductDTO newProductDTO = new ProductDTO();
        BeanUtils.copyProperties(product, newProductDTO);
        return newProductDTO;
    }

    public static Product dtoToEntity(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }
}
