package org.berijalan.stockservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {
    @NotBlank(message = "Nama produk tidak boleh kosong")
    private String name;

    @NotNull(message = "Harga produk tidak boleh kosong")
    private Double price;

    @NotNull(message = "Stok produk tidak boleh kosong")
    @Min(value = 0, message = "Stok produk tidak boleh kurang dari 0")
    private Integer stock;
}
