package org.berijalan.stockservice.service;

import org.berijalan.stockservice.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductEntity saveProduct(ProductEntity productEntity);
    List<ProductEntity> getAllProduct();
}
