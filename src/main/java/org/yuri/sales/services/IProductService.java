package org.yuri.sales.services;

import org.yuri.sales.dtos.SaveProductDto;
import org.yuri.sales.entities.Product;
import org.yuri.sales.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface IProductService {
    List<Product> getAll();
    Product getById(UUID id) throws NotFoundException;
    Product create(SaveProductDto request);
    Product update(UUID id, SaveProductDto request) throws NotFoundException;
    void delete(UUID id) throws NotFoundException;
}
