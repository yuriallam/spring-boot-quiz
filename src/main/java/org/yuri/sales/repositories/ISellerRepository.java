package org.yuri.sales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yuri.sales.entities.Seller;

import java.util.UUID;

public interface ISellerRepository extends JpaRepository<Seller, UUID> {
}
