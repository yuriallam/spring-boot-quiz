package org.yuri.sales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yuri.sales.entities.Product;

import java.util.UUID;

public interface IProductRepository extends JpaRepository<Product, UUID> {
}
