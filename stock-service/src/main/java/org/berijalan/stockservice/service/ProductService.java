package org.berijalan.stockservice.service;

import org.berijalan.stockservice.dto.request.ProductRequest;
import org.berijalan.stockservice.entity.ProductEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ProductEntity saveProduct(ProductRequest productEntity);
    List<ProductEntity> getAllProduct();
    Map<String, Object> getProductWithUsdPrice(Long id);
}
