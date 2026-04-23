package org.berijalan.stockservice.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@FeignClient(name = "currency-api", url = "https://api.freecurrencyapi.com/v1")
public interface CurrencyClient {
    @GetMapping("/latest")
    Map<String, Object> getLatestRates(
            @RequestParam("apikey") String apiKey,
            @RequestParam("base_currency") String baseCurrency,
            @RequestParam("currencies") String targetCurrency
    );
}
