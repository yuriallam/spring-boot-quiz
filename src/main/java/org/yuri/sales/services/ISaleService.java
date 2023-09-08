package org.yuri.sales.services;

import org.yuri.sales.dtos.SaveSaleDto;
import org.yuri.sales.entities.Sale;
import org.yuri.sales.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface ISaleService {
    List<Sale> getAll();
    Sale getById(UUID id) throws NotFoundException;
    Sale create(SaveSaleDto saveSaleDto) throws NotFoundException;
    Sale update(UUID id, SaveSaleDto saveSaleDto) throws NotFoundException;
    void delete(UUID id) throws NotFoundException;
}
