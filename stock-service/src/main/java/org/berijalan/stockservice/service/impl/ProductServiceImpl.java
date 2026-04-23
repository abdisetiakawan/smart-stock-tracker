package org.berijalan.stockservice.service.impl;

import org.berijalan.stockservice.dto.request.ProductRequest;
import org.berijalan.stockservice.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.berijalan.stockservice.rest.CurrencyClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.berijalan.stockservice.repository.ProductRepository;
import org.berijalan.stockservice.service.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CurrencyClient currencyClient;

    @Value("${CURRENCY_API_KEY}")
    private String apiKey;

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

    @Override
    public Map<String, Object> getProductWithUsdPrice(Long id) {
//        mencari product
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
//        melakukan konversi harga ke USD
        Map<String, Object> response = currencyClient.getLatestRates(apiKey, "IDR", "USD");
        Map<String, Object> data = (Map<String, Object>) response.get("data");
        Double usdRate = (Double) data.get("USD");
//        menggabungkan data
        Map<String, Object> result = new HashMap<>();
        result.put("product", product);
        result.put("price_usd", product.getPrice() * usdRate);

        return result;
    }
}
