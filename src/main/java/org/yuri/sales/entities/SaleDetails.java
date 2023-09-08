package org.yuri.sales.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "salesDetails")
public class SaleDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JoinColumn
    @JsonProperty("sale_id")
    @ManyToOne
    private Sale sale;

    @JoinColumn
    @JsonProperty("product_id")
    @ManyToOne
    private Product product;

    @Column
    private Double quantity;

    @Column
    private Double unitPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;
}