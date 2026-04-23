package org.berijalan.stockservice.controller;

import org.berijalan.stockservice.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.berijalan.stockservice.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/stock/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductEntity> create(@RequestBody ProductEntity productEntity) {
        ProductEntity savedProduct = productService.saveProduct(productEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAll() {
        return ResponseEntity.ok(productService.getAllProduct());
    }
}
