package org.yuri.sales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yuri.sales.entities.Product;
import org.yuri.sales.entities.SaleDetails;

import java.util.UUID;

public interface ISaleDetailsRepository extends JpaRepository<SaleDetails, UUID> {
}
