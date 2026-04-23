package org.berijalan.stockservice.controller;

import jakarta.validation.Valid;
import org.berijalan.stockservice.dto.request.ProductRequest;
import org.berijalan.stockservice.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.berijalan.stockservice.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductEntity> create(@Valid @RequestBody ProductRequest productRequest) {
        ProductEntity savedProduct = productService.saveProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAll() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @GetMapping("/{id}/usd")
    public ResponseEntity<Map<String, Object>> getProductInUsd(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductWithUsdPrice(id));
    }
}
