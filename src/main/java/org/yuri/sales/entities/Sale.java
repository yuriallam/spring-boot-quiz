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
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn
    @JsonProperty("client_id")
    private Client client;

    @ManyToOne
    @JoinColumn
    @JsonProperty("seller_id")
    private Seller seller;

    @Column
    private Double total;

    @CreationTimestamp
    private LocalDateTime createdAt;
}