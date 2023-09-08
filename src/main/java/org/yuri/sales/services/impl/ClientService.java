package org.yuri.sales.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yuri.sales.dtos.SaveClientDto;
import org.yuri.sales.entities.Client;
import org.yuri.sales.exceptions.NotFoundException;
import org.yuri.sales.repositories.IClientRepository;
import org.yuri.sales.services.IClientService;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService implements IClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
    private final IClientRepository clientRepository;

    public ClientService(
            IClientRepository clientRepository
    ) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getAll() {
        logger.info("Fetching all Clients from database.");
        List<Client> clients = clientRepository.findAll();
        logger.debug("Fetched {} clients.", clients.size());
        return clients;
    }

    @Override
    public Client getById(UUID id) throws NotFoundException {
        logger.info("Attempting to fetch Client with Id: {}", id);
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Client with Id {} not found.", id);
                    return new NotFoundException("Client not found");
                });
        logger.info("Successfully fetched Client with Id: {}", id);
        return client;
    }

    @Override
    public Client create(SaveClientDto saveClientDto) {
        logger.info("Creating a new Client.");
        Client client = toClient(saveClientDto);
        Client savedClient = clientRepository.save(client);
        logger.info("Successfully created Client with Id: {}", savedClient.getId());
        return savedClient;
    }

    @Override
    public Client update(UUID id, SaveClientDto saveClientDto) throws NotFoundException {
        logger.info("Attempting to update Client with Id: {}", id);
        // Check if client exists
        getById(id);
        Client client = toClient(saveClientDto);
        client.setId(id);
        Client updatedClient = clientRepository.save(client);
        logger.info("Successfully updated Client with Id: {}", id);
        return updatedClient;
    }

    @Override
    public void delete(UUID id) throws NotFoundException {
        logger.info("Attempting to delete Client with Id: {}", id);
        Client client = getById(id);
        clientRepository.delete(client);
        logger.info("Successfully deleted Client with Id: {}", id);
    }

    private Client toClient(SaveClientDto saveClientDto) {
        logger.debug("Converting SaveClientDto to Client entity.");
        return Client.builder()
                .firstName(saveClientDto.getFirstName())
                .lastName(saveClientDto.getLastName())
                .mobile(saveClientDto.getMobile())
                .build();
    }
}

