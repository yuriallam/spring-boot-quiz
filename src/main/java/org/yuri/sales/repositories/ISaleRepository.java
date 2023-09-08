package org.yuri.sales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yuri.sales.entities.Sale;

import java.util.UUID;

public interface ISaleRepository extends JpaRepository<Sale, UUID> {
}
