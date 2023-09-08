package org.yuri.sales.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SaveSaleDetailsDto {

    @JsonProperty("sale_id")
    private UUID saleId;

    @JsonProperty("product_id")
    private UUID productId;

    private Double quantity;

    @JsonProperty("unit_price")
    private Double unitPrice;
}
