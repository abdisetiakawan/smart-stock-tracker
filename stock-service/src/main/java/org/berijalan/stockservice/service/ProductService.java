package org.berijalan.stockservice.service;

import org.berijalan.stockservice.dto.request.ProductRequest;
import org.berijalan.stockservice.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductEntity saveProduct(ProductRequest productEntity);
    List<ProductEntity> getAllProduct();
}
