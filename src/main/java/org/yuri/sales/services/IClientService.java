package org.yuri.sales.services;

import org.yuri.sales.dtos.SaveClientDto;
import org.yuri.sales.entities.Client;
import org.yuri.sales.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface IClientService {
    List<Client> getAll();
    Client getById(UUID id) throws NotFoundException;
    Client create(SaveClientDto saveClientDto);
    Client update(UUID id, SaveClientDto saveClientDto) throws NotFoundException;
    void delete(UUID id) throws NotFoundException;
}
