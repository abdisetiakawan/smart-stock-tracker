package org.berijalan.stockservice.service.impl;

import org.berijalan.stockservice.dto.request.ProductRequest;
import org.berijalan.stockservice.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.berijalan.stockservice.repository.ProductRepository;
import org.berijalan.stockservice.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductEntity> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity saveProduct(ProductRequest productRequest) {
        ProductEntity entity = new ProductEntity();
        entity.setName(productRequest.getName());
        entity.setPrice(productRequest.getPrice());
        entity.setStock(productRequest.getStock());

        return productRepository.save(entity);
    }
}
