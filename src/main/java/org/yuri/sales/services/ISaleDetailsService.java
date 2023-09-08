package org.yuri.sales.services;

import org.yuri.sales.dtos.SaveSaleDetailsDto;
import org.yuri.sales.entities.SaleDetails;
import org.yuri.sales.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface ISaleDetailsService {
    List<SaleDetails> getAll();
    SaleDetails getById(UUID id) throws NotFoundException;
    SaleDetails create(SaveSaleDetailsDto saveSaleDetailsDto) throws NotFoundException;
    SaleDetails update(UUID id, SaveSaleDetailsDto saveSaleDetailsDto) throws NotFoundException;
    void delete(UUID id) throws NotFoundException;
}
