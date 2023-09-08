package org.yuri.sales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yuri.sales.entities.Client;

import java.util.UUID;

public interface IClientRepository extends JpaRepository<Client, UUID> {
}
