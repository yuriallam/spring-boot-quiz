package org.yuri.sales.services;

import org.yuri.sales.dtos.SaveSellerDto;
import org.yuri.sales.entities.Client;
import org.yuri.sales.entities.Seller;
import org.yuri.sales.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface ISellerService {
    List<Seller> getAll();
    Seller create(SaveSellerDto saveSellerDto);
    Seller getById(UUID id) throws NotFoundException;
    Seller update(UUID id, SaveSellerDto saveSellerDto) throws NotFoundException;
    void delete(UUID id) throws NotFoundException;
}
