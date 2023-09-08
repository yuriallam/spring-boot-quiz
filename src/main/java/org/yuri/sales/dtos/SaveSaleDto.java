package org.yuri.sales.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SaveSaleDto {

    @JsonProperty("client_id")
    private UUID clientId;

    @JsonProperty("seller_id")
    private UUID sellerId;

    private Double total;
}
